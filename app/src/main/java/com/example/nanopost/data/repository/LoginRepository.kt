package com.example.nanopost.data.repository

import com.example.nanopost.data.api.TokenResponse
import com.example.nanopost.domen.models.ModelTokenResponse

interface LoginRepository {

    suspend fun login(username: String, password: String): ModelTokenResponse

    suspend fun register(username: String, password: String): ModelTokenResponse

    suspend fun checkUsername(username: String): String

}