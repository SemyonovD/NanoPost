package com.example.nanopost.data.api

import com.example.nanopost.domen.models.Likes
import kotlinx.serialization.Serializable

@Serializable
data class ApiLikes(
    val liked: Boolean,
    val likesCount: Int,
) {
    fun toLikes(): Likes = Likes (
        liked = liked,
        likesCount = likesCount
    )
}
