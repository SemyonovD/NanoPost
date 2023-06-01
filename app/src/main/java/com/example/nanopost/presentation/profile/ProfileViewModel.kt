package com.example.nanopost.presentation.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.nanopost.domen.models.Post
import com.example.nanopost.domen.models.Profile
import com.example.nanopost.domen.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val editProfileUseCase: EditProfileUseCase,
    private val getOurProfileIdUseCase: GetOurProfileIdUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val subscribeProfileUseCase: SubscribeProfileUseCase,
    private val unsubscribeProfileUseCase: UnsubscribeProfileUseCase,
    private val logoutUseCase: LogoutUseCase
): ViewModel() {

    private val _profileLiveData = MutableLiveData<Profile?>()
    val profileLiveData: LiveData<Profile?> = _profileLiveData

    private val _ourProfileIdLiveData = MutableLiveData<String?>()
    val ourProfileIdLiveData: LiveData<String?> = _ourProfileIdLiveData

    private val _postsLiveData = MutableLiveData<PagingData<Post>>()
    val postsLiveData: LiveData<PagingData<Post>> = _postsLiveData

    fun getProfile(profileId: String?) {
        viewModelScope.launch {
            if (profileId == null) {
                _ourProfileIdLiveData.value = getOurProfileIdUseCase()
                _profileLiveData.value = getProfileUseCase(_ourProfileIdLiveData.value)
                _profileLiveData.value?.id?.let {
                    getPostsUseCase(it).cachedIn(viewModelScope).collect { posts ->
                        _postsLiveData.value = posts
                    }
                }
            } else {
                _profileLiveData.value = getProfileUseCase(profileId)
                _ourProfileIdLiveData.value = getOurProfileIdUseCase()
                _profileLiveData.value?.id?.let {
                    getPostsUseCase(it).cachedIn(viewModelScope).collect { posts ->
                        _postsLiveData.value = posts
                    }
                }
            }
        }
    }

    fun editProfile(
        profileId: String?,
        displayName: String?,
        bio: String?,
        avatar: Uri?
    ) {
        viewModelScope.launch {
            editProfileUseCase(
                profileId,
                displayName,
                bio,
                avatar
            )
        }
    }

    fun subscribeProfile(profileId: String) {
        viewModelScope.launch {
            subscribeProfileUseCase(profileId)
        }
    }

    fun unsubscribeProfile(profileId: String) {
        viewModelScope.launch {
            unsubscribeProfileUseCase(profileId)
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}