package com.example.myneighborhoodv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myneighborhoodv2.data.Product
import com.example.myneighborhoodv2.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainCategoryViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    

    private val _Products = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val Products: StateFlow<Resource<List<Product>>> = _Products

    private val pagingInfo = PagingInfo()

    init {
        fetchProducts()
    }



    fun fetchProducts() {
        if (!pagingInfo.isPagingEnd) {
            viewModelScope.launch {
                _Products.emit(Resource.Loading())
                firestore.collection("Products").limit(pagingInfo.ProductsPage * 10).get()
                    .addOnSuccessListener { result ->
                        val Products = result.toObjects(Product::class.java)
                        pagingInfo.isPagingEnd = Products == pagingInfo.oldProducts
                        pagingInfo.oldProducts = Products
                        viewModelScope.launch {
                            _Products.emit(Resource.Success(Products))
                        }
                        pagingInfo.ProductsPage++
                    }.addOnFailureListener {
                        viewModelScope.launch {
                            _Products.emit(Resource.Error(it.message.toString()))
                        }
                    }
            }
        }
    }
}

internal data class PagingInfo(
    var ProductsPage: Long = 1,
    var oldProducts: List<Product> = emptyList(),
    var isPagingEnd: Boolean = false
)