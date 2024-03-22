package com.example.myneighborhoodv2.fragments.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myneighborhoodv2.data.User
import com.example.myneighborhoodv2.databinding.FragmentRegisterBinding
import com.example.myneighborhoodv2.util.Resource
import com.example.myneighborhoodv2.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

private val TAG = "RegisterFragment"

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

        binding.apply {
            nextButton.setOnClickListener {
                val user = User(
                    editEmail.text.toString()
                )
                val password = editPassword.text.toString()
                viewModel.createAccountWithEmailAndPassword(user, password)
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
    }
}

