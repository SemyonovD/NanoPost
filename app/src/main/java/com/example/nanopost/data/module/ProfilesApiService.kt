package com.example.nanopost.data.module

import com.example.nanopost.data.api.ApiProfile
import com.example.nanopost.data.api.ResultResponse
import okhttp3.MultipartBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfilesApiService {

    @GET("profile/search")
    suspend fun findProfiles(
        @Query("query") query: String,
        @Query("count") count: Int?,
        @Query("offset") offset: String?,
    )

    @GET("profile/{profileId}")
    suspend fun getProfile(
        @Path("profileId") profileId: String?
    ): ApiProfile

    @Multipart
    @PATCH("profile/{profileId}")
    suspend fun editProfile(
        @Path("profileId") profileId: String?,
        @Part displayName: MultipartBody.Part? = null,
        @Part bio: MultipartBody.Part? = null,
        @Part avatar: MultipartBody.Part? = null,
    )

    @PUT("profile/{profileId}/subscribe")
    suspend fun subscribeProfile(
        @Path("profileId") profileId: String
    ): ResultResponse

    @DELETE("profile/{profileId}/subscribe")
    suspend fun unsubscribeProfile(
        @Path("profileId") profileId: String
    )

}