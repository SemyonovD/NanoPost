package com.example.nanopost.presentation.image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nanopost.domen.models.Image
import com.example.nanopost.domen.usecase.DeleteImageUseCase
import com.example.nanopost.domen.usecase.GetImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BigImageViewModel @Inject constructor(
    private val getImageUseCase: GetImageUseCase,
    private val deleteImageUseCase: DeleteImageUseCase,
): ViewModel() {

    private val _imageLiveData = MutableLiveData<Image?>()
    val imageLiveData: LiveData<Image?> = _imageLiveData

    private val _deleteResponseLiveData = MutableLiveData<Boolean?>()
    val deleteResponseLiveData: LiveData<Boolean?> = _deleteResponseLiveData

    fun getImage(imageId: String) {
        viewModelScope.launch {
            _imageLiveData.value = getImageUseCase(imageId)
        }
    }

    fun deleteImage() {
        viewModelScope.launch {
            _imageLiveData.value?.let { _deleteResponseLiveData.value = deleteImageUseCase(it.id) }
        }
    }
}