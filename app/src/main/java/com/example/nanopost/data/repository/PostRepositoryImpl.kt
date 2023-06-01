package com.example.nanopost.data.repository

import android.content.ContentResolver
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.paging.*
import com.example.nanopost.data.paging.FeedPagingSource
import com.example.nanopost.data.paging.PostPagingSource
import com.example.nanopost.domen.models.Post
import com.example.nanopost.domen.models.UploadPost
import com.example.nanopost.data.module.PostsApi
import com.example.nanopost.data.module.PostsApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*
import javax.inject.Inject


class PostRepositoryImpl @Inject constructor(
    @PostsApi private val postsApiService: PostsApiService,
    private val contentResolver: ContentResolver,
    private val mimeTypeMap: MimeTypeMap,
    private val imageDir: File,
):PostRepository {

    override fun getFeed(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(30, enablePlaceholders = false),
            pagingSourceFactory = { FeedPagingSource(postsApiService) }
        ).flow.map { pagingData ->
            pagingData.map { it.toPost() }

        }
    }

    override fun getPosts(profileId: String): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(30, enablePlaceholders = false),
            pagingSourceFactory = { PostPagingSource(postsApiService, profileId) }
        ).flow.map { pagingData ->
            pagingData.map { it.toPost() }
        }
    }

    override suspend fun getPost(postId: String): Post {
        return postsApiService.getPost(postId).toPost()
    }

    override suspend fun createPost(post: UploadPost) {

        fun getImageFile(uri: Uri): String? {
            return contentResolver.openInputStream(uri)?.use { inputStream ->
                val mimeType = contentResolver.getType(uri)
                val extension = mimeTypeMap.getExtensionFromMimeType(mimeType)
                val file = File(imageDir, UUID.randomUUID().toString() + ".$extension").also {
                    it.parentFile?.mkdirs()
                    it.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                file.absolutePath
            }
        }

        postsApiService.addPost(
            text = ( if (post.text == "") null else post.text)?.let{
                    MultipartBody.Part
                        .createFormData(
                            "text",
                            it
                        )
                               },
            image1 = post.image1?.let{ getImageFile(it) }?.let{ File(it).asRequestBody() }?.let {
                MultipartBody.Part
                    .createFormData(
                        "image1",
                        "",
                        it
                    )
            }
        )
    }

    override suspend fun deletePost(postId: String):Boolean {
        return postsApiService.deletePost(postId).result
    }

}