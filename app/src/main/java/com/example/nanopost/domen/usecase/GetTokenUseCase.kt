package com.example.nanopost.domen.usecase

import com.example.nanopost.data.repository.PreferencesRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    operator fun invoke(): String? {
        return preferencesRepository.getToken()
    }
}