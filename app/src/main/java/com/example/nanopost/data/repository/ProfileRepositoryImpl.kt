package com.example.nanopost.data.repository

import android.content.ContentResolver
import android.net.Uri
import android.webkit.MimeTypeMap
import com.example.nanopost.data.api.ResultResponse
import com.example.nanopost.domen.models.Profile
import com.example.nanopost.data.module.ProfilesApi
import com.example.nanopost.data.module.ProfilesApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    @ProfilesApi private val api: ProfilesApiService,
    private val contentResolver: ContentResolver,
    private val mimeTypeMap: MimeTypeMap,
    private val imageDir: File,
):ProfileRepository {

    private fun getImageFile(uri: Uri): String? {
        return contentResolver.openInputStream(uri)?.use { inputStream ->
            val mimeType = contentResolver.getType(uri)
            val extension = mimeTypeMap.getExtensionFromMimeType(mimeType)
            val file = File(imageDir, UUID.randomUUID().toString() + ".$extension").also {
                it.parentFile?.mkdirs()
                it.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            file.absolutePath
        }
    }

    private fun getRequestBody(uri: Uri?): RequestBody? {
        val path = uri?.let { getImageFile(it) }
        val requestBody = path?.let { File(it).asRequestBody() }
        return requestBody
    }

    override suspend fun getProfile(profileId: String?): Profile {
        return api.getProfile(profileId).toProfile()
    }

    override suspend fun editProfile(
        profileId: String?,
        displayName: String?,
        bio: String?,
        avatar: Uri?
    ): Boolean {
        return api.editProfile(
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
            avatar = avatar?.let {
                getRequestBody(it)
            }?.let {
                MultipartBody.Part
                    .createFormData(
                        "avatar",
                        "",
                        it
                    )
            },
        ).result
    }

    override suspend fun subscribeProfile(
        profileId: String
    ): Boolean {
        return api.subscribeProfile(profileId).result
    }

    override suspend fun unsubscribeProfile(
        profileId: String
    ): Boolean {
        return api.unsubscribeProfile(profileId).result
    }
}