package com.example.nanopost.domen.usecase

import com.example.nanopost.data.repository.PreferencesRepository
import javax.inject.Inject

class AddTokenUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(token: String?) {
        preferencesRepository.addToken(token)
    }
}