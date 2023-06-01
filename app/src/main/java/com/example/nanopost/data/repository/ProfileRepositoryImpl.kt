package com.example.nanopost.data.repository

import android.net.Uri
import com.example.nanopost.domen.models.Profile
import com.example.nanopost.data.module.ProfilesApi
import com.example.nanopost.data.module.ProfilesApiService
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    @ProfilesApi private val api: ProfilesApiService
):ProfileRepository {

    override suspend fun getProfile(profileId: String?): Profile {
        return api.getProfile(profileId).toProfile()
    }

    override suspend fun editProfile(
        profileId: String?,
        displayName: String?,
        bio: String?,
        avatar: Uri?
    ) {
        api.editProfile(
            profileId,
            displayName = displayName?.let {
                MultipartBody.Part
                    .createFormData(
                        "displayName",
                        it
                    )
            },
            bio = bio?.let {
                MultipartBody.Part
                    .createFormData(
                        "bio",
                        it
                    )
            },
            avatar = null
        )
    }

    override suspend fun subscribeProfile(
        profileId: String
    ) {
        api.subscribeProfile(profileId)
    }

    override suspend fun unsubscribeProfile(
        profileId: String
    ) {
        api.unsubscribeProfile(profileId)
    }
}