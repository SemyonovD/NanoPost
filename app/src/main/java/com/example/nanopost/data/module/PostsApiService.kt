package com.example.nanopost.data.module

import com.example.nanopost.data.api.ApiPost
import com.example.nanopost.data.api.PagedDataResponse
import com.example.nanopost.data.api.ResultResponse
import okhttp3.MultipartBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsApiService {

    @GET("feed")
    suspend fun getFeed(
        @Query("count") count: Int?,
        @Query("offset") offset: String?
    ): PagedDataResponse<ApiPost>

    @GET("posts/{profileId}")
    suspend fun getPosts(
        @Path("profileId") profileId: String?,
        @Query("count") count: Int?,
        @Query("offset") offset: String?
    ): PagedDataResponse<ApiPost>

    @Multipart
    @PUT("post")
    suspend fun addPost(
        @Part text: MultipartBody.Part? = null,
        @Part image1: MultipartBody.Part? = null,
        @Part image2: MultipartBody.Part? = null,
        @Part image3: MultipartBody.Part? = null,
        @Part image4: MultipartBody.Part? = null,
    ):ApiPost

    @GET("post/{postId}")
    suspend fun getPost(
        @Path("postId") postId: String,
    ): ApiPost

    @DELETE("post/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: String,
    ): ResultResponse
}