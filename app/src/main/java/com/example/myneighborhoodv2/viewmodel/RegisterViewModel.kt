package com.example.myneighborhoodv2.viewmodel
import androidx.lifecycle.ViewModel
import com.example.myneighborhoodv2.data.User
import com.example.myneighborhoodv2.util.Constants.USER_COLLECTION
import com.example.myneighborhoodv2.util.RegisterFieldsState
import com.example.myneighborhoodv2.util.RegisterValidation
import com.example.myneighborhoodv2.util.Resource
import com.example.myneighborhoodv2.util.confirmPassword
import com.example.myneighborhoodv2.util.validateEmail
import com.example.myneighborhoodv2.util.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
): ViewModel(){

    private val _register = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val register: Flow<Resource<User>> = _register

    private val _validation = Channel<RegisterFieldsState>()
    val validation  = _validation.receiveAsFlow()

    fun createAccountWithEmailAndPassword(user: User, password: String, confirmPassword: String) {
        if(checkValidation(user, password, confirmPassword)){
        runBlocking {
            _register.emit(Resource.Loading())
        }
        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnSuccessListener {
                it.user?.let{
                    saveUserInfo(it.uid, user)

                }
            }.addOnFailureListener{
                _register.value = Resource.Error(it.message.toString())
            }
        }else{
            val registerFieldsState = RegisterFieldsState(
                validateEmail(user.email), validatePassword(password), confirmPassword(confirmPassword, password)
            )
            runBlocking {
                _validation.send(registerFieldsState)
            }
        }
    }

    private fun saveUserInfo(userUid: String, user: User) {
        db.collection(USER_COLLECTION)
            .document(userUid)
            .set(user)
            .addOnSuccessListener {
                _register.value = Resource.Success(user)

            }.addOnFailureListener {
                _register.value = Resource.Error(it.message.toString())
            }
    }

    private fun checkValidation(user: User, password: String, confirmPassword: String): Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidation = validatePassword(password)
        val confirmPasswordValidation = confirmPassword(confirmPassword, password)
        val shouldRegister = emailValidation is RegisterValidation.Success &&
                passwordValidation is RegisterValidation.Success &&
                confirmPasswordValidation is RegisterValidation.Success
        return shouldRegister
    }

}