package com.example.nanopost.presentation.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.nanopost.domen.models.Image
import com.example.nanopost.domen.usecase.GetImagesUseCase
import com.example.nanopost.domen.usecase.GetOurProfileIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val getOurProfileIdUseCase: GetOurProfileIdUseCase
): ViewModel() {

    private val _imagesLiveData = MutableLiveData<PagingData<Image>>()
    val imagesLiveData: LiveData<PagingData<Image>> = _imagesLiveData

    fun getImages(profileId: String?) {
        viewModelScope.launch {
            if (profileId == null) {
                getOurProfileIdUseCase()?.let {
                    getImagesUseCase(it).collect { images ->
                        _imagesLiveData.value = images
                    }
                }
            } else {
                getImagesUseCase(profileId).collect { images ->
                    _imagesLiveData.value = images
                }
            }
        }
    }
}