package com.example.nanopost.domen.usecase

import com.example.nanopost.data.repository.PostRepository
import com.example.nanopost.domen.models.UploadPost
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val postRepository: PostRepository
){
    suspend operator fun invoke(post: UploadPost) {
        postRepository.createPost(post)
    }
}