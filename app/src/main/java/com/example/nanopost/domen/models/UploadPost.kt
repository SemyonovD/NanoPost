package com.example.nanopost.domen.models

import android.net.Uri

data class UploadPost(
    val text: String? = null,
    val image1: Uri? = null,
    val image2: Uri? = null,
    val image3: Uri? = null,
    val image4: Uri? = null,
)
