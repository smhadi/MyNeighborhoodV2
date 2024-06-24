package com.example.myneighborhoodv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myneighborhoodv2.data.BlogPost
import com.example.myneighborhoodv2.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogPostViewModel@Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel()  {

    private val _BlogPost = MutableStateFlow<Resource<List<BlogPost>>>(Resource.Unspecified())
    val BlogPost: StateFlow<Resource<List<BlogPost>>> = _BlogPost

    private val pagingInfoo = PagingInfoo()

    init {
        fetchBlogPost()
    }


    fun fetchBlogPost() {
        if (!pagingInfoo.isPagingEnd) {
            viewModelScope.launch {
                _BlogPost.emit(Resource.Loading())
                firestore.collection("BlogPost").limit(pagingInfoo.BlogPostPage * 10).get()
                    .addOnSuccessListener { result ->
                        val BlogPost: MutableList<out StateFlow<Resource<List<BlogPost>>>> = result.toObjects(BlogPost::class.java)
//                        pagingInfoo.isPagingEnd = BlogPost == pagingInfoo.oldBlogPost
//                        pagingInfoo.oldBlogPost = BlogPost
                        viewModelScope.launch {
//                            _BlogPost.emit(Resource.Success(BlogPost))
                        }
                        pagingInfoo.BlogPostPage++
                    }.addOnFailureListener {
                        viewModelScope.launch {
                            _BlogPost.emit(Resource.Error(it.message.toString()))
                        }
                    }
            }
        }
    }
}

internal data class PagingInfoo(
    var BlogPostPage: Long = 1,
    var oldBlogPost: List<BlogPost> = emptyList(),
    var isPagingEnd: Boolean = false
)