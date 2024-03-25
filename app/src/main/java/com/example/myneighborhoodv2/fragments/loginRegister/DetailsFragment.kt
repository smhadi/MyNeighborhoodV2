package com.example.myneighborhoodv2.fragments.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.myneighborhoodv2.R
import com.example.myneighborhoodv2.databinding.FragmentDetailsBinding

class DetailsFragment: Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            setFragmentResultListener("requestKey") { key, bundle ->
                val result = bundle.getString(editLName.text.toString().trim(),
                    editFName.text.toString().trim()
                )
                binding.nextButton.setOnClickListener {
                    findNavController().navigate(R.id.action_detailsFragment_to_registerFragment)
                }
            }


        }
//        binding.apply {
//            nextButton.setOnClickListener {
//                val fragment = RegisterFragment()
//                val args = Bundle()
//                val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
//                radioGroup.setOnCheckedChangeListener { group, checkedId ->
//                    val radioButton = group.findViewById<RadioButton>(checkedId)
//                    val selectedValue = radioButton.text.toString()
//                    args.putString("FName", editFName.text.toString().trim())
//                    args.putString("LName", editLName.text.toString().trim())
//                    args.putString("Gender", selectedValue)
//                }
//                fragment.arguments = args
//
//                findNavController().navigate(R.id.action_detailsFragment_to_registerFragment)
//
//            }
//        }

    }
}
