package com.example.nanopost.data.api

import com.example.nanopost.data.module.NetworkModule
import com.example.nanopost.data.module.NetworkModule.convertLongToTime
import com.example.nanopost.domen.models.Image
import kotlinx.serialization.Serializable

@Serializable
data class ApiImage(
    val id: String,
    val owner: ApiProfileCompact,
    val dateCreated: Long,
    val sizes: List<ApiImageSize>,
) {
    fun toImage(): Image = Image(
        id = id,
        owner = owner.toProfileCompact(),
        dateCreated = convertLongToTime(dateCreated),
        sizes = sizes.map { it.toImageSize() }
    )
}
