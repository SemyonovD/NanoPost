package com.example.nanopost.presentation.create_post

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nanopost.domen.models.Post
import com.example.nanopost.domen.models.UploadPost
import com.example.nanopost.domen.usecase.CreatePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val createPostUseCase: CreatePostUseCase
) : ViewModel() {

    private val _imageUriLiveData = MutableLiveData<Uri?>()
    val imageUriLiveData: LiveData<Uri?> = _imageUriLiveData

    fun saveImageUri(uri: Uri?) {
        _imageUriLiveData.value = uri
    }

    fun deleteImageUri() {
        _imageUriLiveData.value = null
    }

    fun createPost(text: String?) {
        viewModelScope.launch {
                createPostUseCase(UploadPost(text,_imageUriLiveData.value,null,null) )
        }
    }

}