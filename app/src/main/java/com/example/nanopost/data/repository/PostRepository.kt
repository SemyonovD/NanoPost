package com.example.nanopost.data.repository

import androidx.paging.PagingData
import com.example.nanopost.domen.models.Post
import com.example.nanopost.domen.models.UploadPost
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun getFeed(): Flow<PagingData<Post>>

    fun getPosts(profileId: String): Flow<PagingData<Post>>

    suspend fun getPost(postId: String): Post

    suspend fun createPost(post: UploadPost): Post

    suspend fun deletePost(postId: String):Boolean

}