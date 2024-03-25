package com.example.myneighborhoodv2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myneighborhoodv2.data.User
import com.example.myneighborhoodv2.util.Constants
import com.example.myneighborhoodv2.util.RegisterFieldsState
import com.example.myneighborhoodv2.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


@HiltViewModel
class NeighborhoodViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
): ViewModel() {

    private val _register = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val register: Flow<Resource<User>> = _register


    private val _validation = Channel<RegisterFieldsState>()
    val validation = _validation.receiveAsFlow()

    private fun saveUserInfo(userUid: String, user: User) {

        db.collection(Constants.USER_COLLECTION)
            .document(userUid)
            .set(user)
            .addOnSuccessListener {
                _register.value = Resource.Success(user)

            }.addOnFailureListener {
                _register.value = Resource.Error(it.message.toString())
            }

    }
}