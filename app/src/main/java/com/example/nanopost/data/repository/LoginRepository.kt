package com.example.nanopost.data.repository

import com.example.nanopost.data.api.TokenResponse

interface LoginRepository {

    suspend fun login(username: String, password: String): TokenResponse

    suspend fun register(username: String, password: String): TokenResponse

    suspend fun checkUsername(username: String): String

}