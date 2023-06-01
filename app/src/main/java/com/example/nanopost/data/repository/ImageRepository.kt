package com.example.nanopost.data.repository

import androidx.paging.PagingData
import com.example.nanopost.domen.models.Image
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    fun getImages(profileId: String): Flow<PagingData<Image>>

    suspend fun getImage(imageId: String): Image

    //suspend fun uploadImage(uri: Uri)

    suspend fun  deleteImage(imageId: String): Boolean

}