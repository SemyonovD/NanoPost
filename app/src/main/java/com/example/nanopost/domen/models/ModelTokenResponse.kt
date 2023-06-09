package com.example.nanopost.domen.models

data class ModelTokenResponse(
    val token: String? = null,
    val userId: String? = null,
    val error: String = "OK",
)
