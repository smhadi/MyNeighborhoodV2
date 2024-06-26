package com.example.myneighborhoodv2.fragments.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myneighborhoodv2.R
import com.example.myneighborhoodv2.adapters.ProductsAdapter
import com.example.myneighborhoodv2.databinding.FragmentMainCategoryBinding
import com.example.myneighborhoodv2.util.Resource
import com.example.myneighborhoodv2.viewmodel.MainCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

private val TAG = "MainCategoryFragment"

@AndroidEntryPoint
class MainCategoryFragment : Fragment(R.layout.fragment_main_category) {

    private lateinit var binding: FragmentMainCategoryBinding
    private lateinit var productsAdapter: ProductsAdapter
    private val viewModel by viewModels<MainCategoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupProducts()


//        ProductsAdapter.onClick = {
//            val b = Bundle().apply { putParcelable("product",it) }
//            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment,b)
//        }


        lifecycleScope.launchWhenStarted {
            viewModel.Products.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.productsProgressbar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        productsAdapter.differ.submitList(it.data)
                        binding.productsProgressbar.visibility = View.GONE


                    }
                    is Resource.Error -> {
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        binding.productsProgressbar.visibility = View.GONE

                    }
                    else -> Unit
                }
            }
        }

//        binding.nestedScrollMainCategory.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
//            if (v.getChildAt(0).bottom <= v.height + scrollY) {
//                viewModel.fetchProducts()
//            }
//        })
    }

    private fun setupProducts() {
        productsAdapter = ProductsAdapter()
        binding.rvProducts.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = productsAdapter
        }
    }



    private fun hideLoading() {
        binding.productsProgressbar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.productsProgressbar.visibility = View.VISIBLE

    }


//    override fun onResume() {
//        super.onResume()
//
//        showBottomNavigationView()
//    }

}