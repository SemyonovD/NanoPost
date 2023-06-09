package com.example.nanopost.data.repository

import android.net.Uri
import com.example.nanopost.domen.models.Profile

interface ProfileRepository {

    suspend fun getProfile(profileId: String?):Profile

    suspend fun editProfile(
        profileId: String?,
        displayName: String?,
        bio: String?,
        avatar: Uri?
    ): Boolean

    suspend fun subscribeProfile(profileId: String): Boolean

    suspend fun unsubscribeProfile(profileId: String): Boolean


}