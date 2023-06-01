package com.example.nanopost.domen.models

data class Post(
    val id: String,
    val owner: ProfileCompact,
    val dateCreated: String,
    val text: String? = null,
    val images: List<Image>,
    val likes: Likes,
)
