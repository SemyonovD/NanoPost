package com.example.nanopost.data.api

import com.example.nanopost.data.convertLongToTime
import com.example.nanopost.domen.models.Post
import kotlinx.serialization.Serializable

@Serializable
data class ApiPost(
    val id: String,
    val owner: ApiProfileCompact,
    val dateCreated: Long,
    val text: String? = null,
    val images: List<ApiImage>,
    val likes: ApiLikes,
) {
    fun toPost():Post = Post(
        id = id,
        owner = owner.toProfileCompact(),
        dateCreated = dateCreated.convertLongToTime(),
        text = text,
        images = images.map{ it.toImage() },
        likes = likes.toLikes(),
    )
}
