package com.example.myneighborhoodv2.fragments.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myneighborhoodv2.data.User
import com.example.myneighborhoodv2.databinding.FragmentRegisterBinding
import com.example.myneighborhoodv2.util.RegisterValidation
import com.example.myneighborhoodv2.util.Resource
import com.example.myneighborhoodv2.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val TAG = "RegisterFragment"

//@Suppress("DEPRECATION")
@AndroidEntryPoint
class RegisterFragment: Fragment () {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> ()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragment = RegisterFragment()

        binding.apply {

            nextButton.setOnClickListener {
                val result = "result"
                setFragmentResult("requestKey", bundleOf("data" to result))
                val user = User(
                    editEmail.text.toString().trim()
//                    FName.toString(),
//                    LName.toString(),
//                    gender.toString()
                )
                val password = editPassword.text.toString()
                val confirmPassword = editConfirmPassword.text.toString()
                viewModel.createAccountWithEmailAndPassword(user, password, confirmPassword)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.register.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.nextButton.startAnimation()
                    }

                    is Resource.Success -> {
                        Log.d("test", it.data.toString())
                        binding.nextButton.revertAnimation()

                    }

                    is Resource.Error -> {
                        Log.e(TAG, it.message.toString())
                        binding.nextButton.revertAnimation()
                    }

                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.validation.collect{
                validation ->
                if(validation.email is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.editEmail.apply{
                            requestFocus()
                            error = validation.email.message
                        }
                    }
                }
                if(validation.password is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.editPassword.apply{
                            requestFocus()
                            error = validation.password.message
                        }
                    }
                }
                if(validation.confirmPassword is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.editConfirmPassword.apply{
                            requestFocus()
                            error = validation.confirmPassword.message
                        }
                    }
                }
            }
        }
    }
}

