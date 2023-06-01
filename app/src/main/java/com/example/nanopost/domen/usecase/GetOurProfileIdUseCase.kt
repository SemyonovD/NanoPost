package com.example.nanopost.domen.usecase

import com.example.nanopost.data.repository.PreferencesRepository
import javax.inject.Inject

class GetOurProfileIdUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(): String? {
        return preferencesRepository.getProfileId()
    }

}