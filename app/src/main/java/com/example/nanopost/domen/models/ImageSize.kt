package com.example.nanopost.domen.models

import com.example.nanopost.data.api.ApiImageSize

data class ImageSize(
    val width: Int,
    val height: Int,
    val url: String,
) {
    fun toApiImageSize() : ApiImageSize = ApiImageSize (
        width = width,
        height = height,
        url = url
    )
}
