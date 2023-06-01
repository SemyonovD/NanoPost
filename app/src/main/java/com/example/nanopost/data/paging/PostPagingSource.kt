package com.example.nanopost.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nanopost.data.api.ApiPost
import com.example.nanopost.data.module.PostsApiService

class PostPagingSource(
    private val apiService: PostsApiService,
    private val profileId: String
) : PagingSource<String, ApiPost>() {

    override fun getRefreshKey(
        state: PagingState<String, ApiPost>
    ): String? {
        return null
    }

    override suspend fun load(
        params: LoadParams<String>,
    ): LoadResult<String, ApiPost> {
        return try {
            val response = apiService.getPosts(
                profileId = profileId,
                count = params.loadSize,
                offset = params.key,
            )
            LoadResult.Page(
                data = response.items,
                prevKey = null,
                nextKey = response.offset,

            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}