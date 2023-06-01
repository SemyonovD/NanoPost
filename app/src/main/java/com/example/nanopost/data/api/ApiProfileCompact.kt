package com.example.nanopost.data.api

import com.example.nanopost.domen.models.ProfileCompact
import kotlinx.serialization.Serializable

@Serializable
data class ApiProfileCompact(
    val id: String,
    val username: String,
    val displayName: String? = null,
    val avatarUrl: String? = null,
    val subscribed: Boolean,
) {
    fun toProfileCompact(): ProfileCompact = ProfileCompact(
        id = id,
        username = username,
        displayName = displayName,
        avatarUrl = avatarUrl,
        subscribed = subscribed
    )
}
