package com.example.nanopost.domen.usecase

import com.example.nanopost.data.repository.ImageRepository
import javax.inject.Inject

class DeleteImageUseCase @Inject constructor(
    private val imagesRepository: ImageRepository
) {

    suspend operator fun invoke(imageId: String):Boolean {
        return imagesRepository.deleteImage(imageId)
    }

}