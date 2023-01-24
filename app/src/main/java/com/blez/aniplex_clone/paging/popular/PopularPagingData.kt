package com.blez.aniplex_clone.paging.popular

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blez.aniplex_clone.Network.AnimeInterface
import com.blez.aniplex_clone.data.PopularDataItem

class PopularPagingData(private val animeAPI : AnimeInterface) : PagingSource<Int,PopularDataItem>(){
    override fun getRefreshKey(state: PagingState<Int, PopularDataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularDataItem> {
     return try {

            val position = params.key?:1
         val response = animeAPI.getPopularAnime(position).body()
         LoadResult.Page(
             data = response!!,
             prevKey = if (position == 1) 0 else position -1,
             nextKey = position +1
         )

     }catch (e : java.lang.Exception)
     {
         LoadResult.Error(e)
     }
    }

}