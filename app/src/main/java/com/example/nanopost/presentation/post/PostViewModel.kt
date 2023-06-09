package com.example.nanopost.presentation.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nanopost.domen.models.Post
import com.example.nanopost.domen.usecase.DeletePostUseCase
import com.example.nanopost.domen.usecase.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
): ViewModel() {

    private val _postLiveData = MutableLiveData<Post?>()
    val postLiveData: LiveData<Post?> = _postLiveData

    private val _deleteResponseLiveData = MutableLiveData<Boolean?>()
    val deleteResponseLiveData: LiveData<Boolean?> = _deleteResponseLiveData

    fun getPost(postId: String) {
        viewModelScope.launch {
            _postLiveData.value = getPostUseCase(postId)
        }
    }

    fun deletePost() {
        viewModelScope.launch {
            _postLiveData.value?.let { _deleteResponseLiveData.value = deletePostUseCase(it.id) }
        }
    }
}