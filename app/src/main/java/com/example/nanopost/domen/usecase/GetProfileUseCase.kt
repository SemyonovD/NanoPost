package com.example.nanopost.domen.usecase

import com.example.nanopost.data.repository.ProfileRepository
import com.example.nanopost.domen.models.Profile
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {
    suspend operator fun invoke(profileId: String?):Profile {
        return profileRepository.getProfile(profileId)
    }
}