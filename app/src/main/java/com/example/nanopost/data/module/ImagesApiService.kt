package com.example.nanopost.data.module

import com.example.nanopost.data.api.ApiImage
import com.example.nanopost.data.api.PagedDataResponse
import com.example.nanopost.data.api.ResultResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ImagesApiService {

    @GET("images/{profileId}")
    suspend fun getImages(
        @Path("profileId") profileId: String,
        @Query("count") count: Int?,
        @Query("offset") offset: String?,
    ): PagedDataResponse<ApiImage>

    @Multipart
    @PUT("image")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
    ): ApiImage

    @GET("image/{imageId}")
    suspend fun getImage(
        @Path("imageId") imageId: String
    ): ApiImage

    @DELETE("image/{imageId}")
    suspend fun deleteImage(
        @Path("imageId") imageId: String
    ): ResultResponse
}