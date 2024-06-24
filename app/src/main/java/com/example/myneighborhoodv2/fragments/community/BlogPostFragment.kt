package com.example.myneighborhoodv2.fragments.community

import android.content.ContentValues.TAG
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
import com.example.myneighborhoodv2.adapters.BlogPostAdapter
import com.example.myneighborhoodv2.databinding.FragmentBlogPostsBinding
import com.example.myneighborhoodv2.util.Resource
import com.example.myneighborhoodv2.viewmodel.BlogPostViewModel
import kotlinx.coroutines.flow.collectLatest

private val TAG = "BlogPostFragment"

class BlogPostFragment : Fragment(R.layout.fragment_blog_posts) {

    private lateinit var binding: FragmentBlogPostsBinding
    private lateinit var blogPostAdapter: BlogPostAdapter
    private val viewModel by viewModels<BlogPostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlogPostsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBlogPost()


//        BlogPostAdapter.onClick = {
//            val b = Bundle().apply { putParcelable("product",it) }
//            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment,b)
//        }


        lifecycleScope.launchWhenStarted {
            viewModel.BlogPost.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.blogPostsProgressbar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        blogPostAdapter.differ.submitList(it.data)
                        binding.blogPostsProgressbar.visibility = View.GONE


                    }
                    is Resource.Error -> {
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        binding.blogPostsProgressbar.visibility = View.GONE

                    }
                    else -> Unit
                }
            }
        }

//        binding.nestedScrollHome.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
//            if (v.getChildAt(0).bottom <= v.height + scrollY) {
//                viewModel.fetchBlogPost()
//            }
//        })
    }

    private fun setupBlogPost() {
        blogPostAdapter = BlogPostAdapter()
        binding.blogPostsRecyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = blogPostAdapter
        }
    }



    private fun hideLoading() {
        binding.blogPostsProgressbar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.blogPostsProgressbar.visibility = View.VISIBLE

    }


//    override fun onResume() {
//        super.onResume()
//
//        showBottomNavigationView()
//    }

}



