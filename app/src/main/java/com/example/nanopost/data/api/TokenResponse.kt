package com.example.nanopost.data.api

import com.example.nanopost.domen.models.ModelTokenResponse
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val token: String,
    val userId: String,
) {
    fun toModelTokenResponse(): ModelTokenResponse = ModelTokenResponse(
        token = token,
        userId = userId,
    )
}
