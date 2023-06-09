package com.example.nanopost.domen.models

import android.net.Uri

data class UploadPost(
    val text: String? = null,
    val image0: Uri? = null,
    val image1: Uri? = null,
    val image2: Uri? = null,
    val image3: Uri? = null,
)
