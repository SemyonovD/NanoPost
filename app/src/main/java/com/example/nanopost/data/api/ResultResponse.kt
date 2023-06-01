package com.example.nanopost.data.api

import kotlinx.serialization.Serializable

@Serializable
data class ResultResponse(
    val result: Boolean = true
)
