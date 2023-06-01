package com.example.nanopost.domen.models


data class Profile(
    val id: String,
    val username: String,
    val displayName: String?,
    val bio: String?,
    val avatarId: String? = null,
    val avatarSmall: String? = null,
    val avatarLarge: String? = null,
    val subscribed: Boolean,
    val subscribersCount: Int,
    val postsCount: Int,
    val imagesCount: Int,
    val images: List<Image>
)
