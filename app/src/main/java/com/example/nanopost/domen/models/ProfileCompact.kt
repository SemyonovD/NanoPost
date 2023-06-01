package com.example.nanopost.domen.models

data class ProfileCompact(
    val id: String,
    val username: String,
    val displayName: String? = null,
    val avatarUrl: String? = null,
    val subscribed: Boolean,
)
