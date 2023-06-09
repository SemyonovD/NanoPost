package com.example.nanopost.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nanopost.domen.usecase.CheckUsernameUseCase
import com.example.nanopost.domen.usecase.GetOurProfileIdUseCase
import com.example.nanopost.domen.usecase.LoginUseCase
import com.example.nanopost.domen.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getOurProfileIdUseCase: GetOurProfileIdUseCase,
    private val checkUsernameUseCase: CheckUsernameUseCase,
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {
    private var job: Job? = null

    private val _errorUsernameLiveData = MutableLiveData<String?>()
    val errorUsernameLiveData: LiveData<String?> = _errorUsernameLiveData

    private val _errorLiveData = MutableLiveData<String?>()
    val errorLiveData: LiveData<String?> = _errorLiveData

    private val _profileIdLiveData = MutableLiveData<String?>()
    val profileIdLiveData: LiveData<String?> = _profileIdLiveData

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _errorLiveData.value = loginUseCase(username, password)
            if (_errorLiveData.value == "OK") {
                _profileIdLiveData.value = getOurProfileIdUseCase()
            }
        }
    }

    fun checkLogin() {
        viewModelScope.launch {
            _profileIdLiveData.value = getOurProfileIdUseCase()
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            _errorLiveData.value = registerUseCase(username, password)
            if (_errorLiveData.value == "OK") {
                _profileIdLiveData.value = getOurProfileIdUseCase()
            }
        }
    }

    fun checkUsername(username: String) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(500)
            val message = checkUsernameUseCase(username)
            if (message == "500") {
                _errorLiveData.value = message
            } else {
                _errorUsernameLiveData.value = message
            }
        }
    }
}