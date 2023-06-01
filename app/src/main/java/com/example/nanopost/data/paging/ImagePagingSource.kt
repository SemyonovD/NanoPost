package com.example.nanopost.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nanopost.data.api.ApiImage
import com.example.nanopost.data.module.ImagesApiService

class ImagePagingSource(
    private val apiService: ImagesApiService,
    private val profileId: String,
) : PagingSource<String, ApiImage>() {

    override fun getRefreshKey(
        state: PagingState<String, ApiImage>
    ): String? {
        return null
    }
    override suspend fun load(
        params: LoadParams<String>,
    ): LoadResult<String, ApiImage> {
        return try {
            val response = apiService.getImages(
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