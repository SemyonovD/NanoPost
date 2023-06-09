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
import okhttp3.RequestBody
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

    private fun getImageFile(uri: Uri): String? {
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

    private fun getRequestBody(uri: Uri?): RequestBody? {
        val path = uri?.let { getImageFile(it) }
        val requestBody = path?.let { File(it).asRequestBody() }
        return requestBody
    }

    override suspend fun createPost(post: UploadPost): Post {
        return postsApiService.addPost(
            text = (if (post.text == "") null else post.text)?.let {
                    MultipartBody.Part
                        .createFormData(
                            "text",
                            it
                        )
            },
            image1 = post.image0?.let {
                getRequestBody(it)
            }?.let{
                MultipartBody.Part
                    .createFormData(
                        "image1",
                        "",
                        it
                    )
            },
            image2 = post.image1?.let {
                getRequestBody(it)
            }?.let{
                MultipartBody.Part
                    .createFormData(
                        "image2",
                        "",
                        it
                    )
            },
            image3 = post.image2?.let {
                getRequestBody(it)
            }?.let{
                MultipartBody.Part
                    .createFormData(
                        "image3",
                        "",
                        it
                    )
            },
            image4 = post.image3?.let {
                getRequestBody(it)
            }?.let{
                MultipartBody.Part
                    .createFormData(
                        "image4",
                        "",
                        it
                    )
            },
        ).toPost()
    }

    override suspend fun deletePost(postId: String):Boolean {
        return postsApiService.deletePost(postId).result
    }
}