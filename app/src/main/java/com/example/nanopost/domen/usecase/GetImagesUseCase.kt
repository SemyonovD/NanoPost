package com.example.nanopost.domen.usecase

import com.example.nanopost.data.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData
import com.example.nanopost.domen.models.Image
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {

    operator fun invoke(profileId: String): Flow<PagingData<Image>> {
        return imageRepository.getImages(profileId)
    }

}