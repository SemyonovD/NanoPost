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

    private val _postResponseLiveData = MutableLiveData<Post?>()
    val postResponseLiveData: LiveData<Post?> = _postResponseLiveData

    private val _imageListLiveData = MutableLiveData<MutableList<Uri>?>()
    val imageListLiveData: LiveData<MutableList<Uri>?> = _imageListLiveData

    fun saveImageUri(uri: Uri) {
        if (_imageListLiveData.value != null) {
            val listUri = _imageListLiveData.value
            listUri?.add(uri)
            _imageListLiveData.value = listUri
        } else {
            _imageListLiveData.value = mutableListOf(
                uri,
            )
        }
    }

    fun deleteImageUri(index: Int) {
        val listUri = _imageListLiveData.value
        listUri?.remove(listUri[index])
        _imageListLiveData.value = listUri
    }

    fun createPost(text: String?) {
        viewModelScope.launch {
            _postResponseLiveData.value = createPostUseCase (
                UploadPost (
                    text,
                    _imageListLiveData.value?.getOrNull(0),
                    _imageListLiveData.value?.getOrNull(1),
                    _imageListLiveData.value?.getOrNull(2),
                    _imageListLiveData.value?.getOrNull(3),
                )
            )
        }
    }
}