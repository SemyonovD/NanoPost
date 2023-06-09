package com.example.nanopost.domen.usecase

import com.example.nanopost.data.repository.PostRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: String):Boolean {
        return postRepository.deletePost(postId)
    }

}