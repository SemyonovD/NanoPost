package com.example.nanopost.data.api


import com.example.nanopost.domen.models.Profile
import kotlinx.serialization.Serializable

@Serializable
data class ApiProfile(
    val id: String,
    val username: String,
    val displayName: String? = null,
    val bio: String? = null,
    val avatarId: String? = null,
    val avatarSmall: String? = null,
    val avatarLarge: String? = null,
    val subscribed: Boolean,
    val subscribersCount: Int,
    val postsCount: Int,
    val imagesCount: Int,
    val images: List<ApiImage>
) {
    fun toProfile(): Profile = Profile(
        id = id,
        username = username,
        displayName = displayName,
        bio = bio,
        avatarId = avatarId,
        avatarSmall = avatarSmall,
        avatarLarge = avatarLarge,
        subscribed = subscribed,
        subscribersCount = subscribersCount,
        postsCount = postsCount,
        imagesCount = imagesCount,
        images = images.map { it.toImage() }


    )
}
