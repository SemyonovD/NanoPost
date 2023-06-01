package com.example.nanopost.data.api

import kotlinx.serialization.Serializable

@Serializable
data class CheckUsernameResponse(
    val result: String,
)
