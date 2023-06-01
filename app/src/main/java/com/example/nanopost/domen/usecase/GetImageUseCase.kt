package com.example.nanopost.domen.usecase

import com.example.nanopost.data.repository.ImageRepository
import com.example.nanopost.domen.models.Image
import javax.inject.Inject

class GetImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(imageId: String): Image {
        return imageRepository.getImage(imageId)
    }
}