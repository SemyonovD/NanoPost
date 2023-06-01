package com.example.nanopost.domen.usecase

import com.example.nanopost.data.repository.ProfileRepository
import javax.inject.Inject

class UnsubscribeProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(profileId: String) {
        profileRepository.unsubscribeProfile(profileId)
    }
}