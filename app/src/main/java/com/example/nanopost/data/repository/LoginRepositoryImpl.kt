package com.example.nanopost.data.repository

import com.example.nanopost.data.api.TokenResponse
import com.example.nanopost.data.api.RegisterRequest
import com.example.nanopost.data.module.AuthApi
import com.example.nanopost.data.module.AuthApiService
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    @AuthApi private val authApi: AuthApiService,
): LoginRepository {

    override suspend fun login(username: String, password: String): TokenResponse {
        return authApi.auth(username, password)
    }

    override suspend fun register(username: String, password: String): TokenResponse {
        val body = RegisterRequest(username, password)
        return authApi.register(body)
    }

    override suspend fun checkUsername(username: String): String {
        return authApi.checkUsername(username).result
    }
}