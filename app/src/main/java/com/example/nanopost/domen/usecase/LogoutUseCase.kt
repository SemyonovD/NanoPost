package com.example.nanopost.domen.usecase

import com.example.nanopost.data.repository.PreferencesRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) {
    suspend operator fun invoke() {
        preferencesRepository.addToken(null)
        preferencesRepository.addProfileId(null)
    }
}