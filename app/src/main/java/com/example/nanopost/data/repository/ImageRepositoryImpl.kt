package com.example.nanopost.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.nanopost.data.paging.ImagePagingSource
import com.example.nanopost.domen.models.Image
import com.example.nanopost.data.module.ImagesApi
import com.example.nanopost.data.module.ImagesApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @ImagesApi private val imagesApiService: ImagesApiService,
):ImageRepository {

    override suspend fun getImage(imageId: String): Image {
        return imagesApiService.getImage(imageId).toImage()
    }

    override fun getImages(profileId: String): Flow<PagingData<Image>> {
        return Pager(
            config = PagingConfig(30, enablePlaceholders = false),
            pagingSourceFactory = { ImagePagingSource(imagesApiService, profileId) }
        ).flow.map { pagingData ->
            pagingData.map { it.toImage() }

        }
    }

/*    override suspend fun uploadImage(uri: Uri) {
        imagesApiService.uploadImage(
            image = MultipartBody.Part
                .createFormData(
                    "image",
                    "",
                    imagefile.asRequestBody()
                )
        )
    }*/

    override suspend fun deleteImage(imageId: String):Boolean {
        return imagesApiService.deleteImage(imageId).result
    }
}