package com.example.nanopost.data.module

import com.example.nanopost.data.api.CheckUsernameResponse
import com.example.nanopost.data.api.RegisterRequest
import com.example.nanopost.data.api.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

    @GET("auth/checkUsername")
    suspend fun checkUsername(
        @Query("username") username: String
    ): CheckUsernameResponse

    @GET("auth/login")
    suspend fun auth(
        @Query("username") username: String,
        @Query("password") password: String,
    ): TokenResponse

    @POST("auth/register")
    suspend fun register(
        @Body body: RegisterRequest
    ): TokenResponse


}