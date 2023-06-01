package com.example.nanopost.domen.usecase

import android.net.Uri
import com.example.nanopost.data.repository.ProfileRepository
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(
        profileId: String?,
        displayName: String?,
        bio: String?,
        avatar: Uri?
    ) {
        profileRepository.editProfile(
            profileId,
            displayName,
            bio,
            avatar
        )
    }
}