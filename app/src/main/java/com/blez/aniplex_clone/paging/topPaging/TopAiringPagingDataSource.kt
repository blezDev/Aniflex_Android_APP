package com.blez.aniplex_clone.paging.topPaging

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blez.aniplex_clone.Network.AnimeInterface
import com.blez.aniplex_clone.data.TopAiringDataItem
import com.blez.aniplex_clone.paging.PagingDataSource

class TopAiringPagingDataSource(private val animeAPI : AnimeInterface) : PagingSource<Int, TopAiringDataItem>() {
    override fun getRefreshKey(state: PagingState<Int, TopAiringDataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopAiringDataItem> {
        val key = params.key ?: 1
        val response = animeAPI.getTopAiring(key).body()
        return try {
           LoadResult.Page(
               data = response!!,
               prevKey = if (key==1) null else key -1,
               nextKey = key + 1
           )

        }catch (e : Exception){
            LoadResult.Error(e)
        }


    }
}