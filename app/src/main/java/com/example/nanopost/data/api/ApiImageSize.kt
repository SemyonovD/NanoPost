package com.example.nanopost.data.api

import com.example.nanopost.domen.models.ImageSize
import kotlinx.serialization.Serializable

@Serializable
data class ApiImageSize(
    val width: Int,
    val height: Int,
    val url: String,
){
    fun toImageSize(): ImageSize = ImageSize(
        width = width,
        height = height,
        url = url
    )
}
