package com.example.nanopost.domen.usecase

import androidx.paging.PagingData
import com.example.nanopost.data.repository.PostRepository
import com.example.nanopost.domen.models.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {

    operator fun invoke(profileId: String): Flow<PagingData<Post>> {
        return postRepository.getPosts(profileId)
    }
}