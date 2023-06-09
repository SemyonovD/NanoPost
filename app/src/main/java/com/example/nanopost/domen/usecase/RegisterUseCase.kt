package com.example.nanopost.domen.usecase

import com.example.nanopost.data.repository.LoginRepository
import com.example.nanopost.data.repository.PreferencesRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val preferencesRepository: PreferencesRepository,
) {
    suspend operator fun invoke(username: String, password: String): String {
        val response = loginRepository.register(username, password)
        preferencesRepository.addToken(response.token)
        preferencesRepository.addProfileId(response.userId)
        return response.error
    }
}