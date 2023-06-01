package com.example.nanopost.data.repository

interface PreferencesRepository {

    suspend fun addToken(token: String?)

    fun getToken(): String?

    suspend fun addProfileId(id: String?)

    suspend fun getProfileId(): String?

}