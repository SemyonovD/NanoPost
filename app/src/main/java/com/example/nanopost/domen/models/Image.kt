package com.example.nanopost.domen.models

data class Image(
    val id: String,
    val owner: ProfileCompact,
    val dateCreated: String,
    val sizes: List<ImageSize>,
)
