package com.example.nanopost.presentation.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.nanopost.domen.models.Post
import com.example.nanopost.domen.usecase.GetFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getFeedUseCase: GetFeedUseCase,
): ViewModel() {

    private val _feedLiveData = MutableLiveData<PagingData<Post>>()
    val feedLiveData: LiveData<PagingData<Post>> = _feedLiveData

    fun getFeed() {
        viewModelScope.launch {
            getFeedUseCase().collect { feed ->
                _feedLiveData.value = feed
            }
        }
    }
}