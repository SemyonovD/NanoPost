package com.example.nanopost.data.repository

import com.example.nanopost.R
import com.example.nanopost.data.api.TokenResponse
import com.example.nanopost.data.api.RegisterRequest
import com.example.nanopost.data.module.AuthApi
import com.example.nanopost.data.module.AuthApiService
import com.example.nanopost.domen.models.ModelTokenResponse
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    @AuthApi private val authApi: AuthApiService,
): LoginRepository {

    override suspend fun login(username: String, password: String): ModelTokenResponse {
        return try {
            authApi.auth(username, password).toModelTokenResponse()
        } catch (e:Exception) {
            ModelTokenResponse(null,null,"login500")
        }
    }

    override suspend fun register(username: String, password: String): ModelTokenResponse {
        val body = RegisterRequest(username, password)
        return try {
            authApi.register(body).toModelTokenResponse()
        } catch (e:Exception) {
            ModelTokenResponse(null,null,"register500")
        }
    }

    override suspend fun checkUsername(username: String): String {
        return try {
            authApi.checkUsername(username).result
        } catch (e:Exception) {
            "500"
        }
    }
}