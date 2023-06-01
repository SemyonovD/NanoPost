package com.example.nanopost.domen.usecase

import com.example.nanopost.data.repository.PostRepository
import com.example.nanopost.domen.models.Post
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(postId: String): Post {
        return postRepository.getPost(postId)
    }

}