package com.blez.aniplex_clone.paging.popular

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blez.aniplex_clone.Network.AnimeInterface
import com.blez.aniplex_clone.data.PopularModelItem

class PopularPagingData(private val animeAPI : AnimeInterface) : PagingSource<Int, PopularModelItem>(){
    override fun getRefreshKey(state: PagingState<Int, PopularModelItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularModelItem> {
     return try {

            val position = params.key?:1
         val response = animeAPI.getPopularAnime(position).body()
         LoadResult.Page(
             data = response!!.results.map { it.toPopularModelItem() },
             prevKey = if (position == 1) 0 else position -1,
             nextKey = position +1
         )

     }catch (e : java.lang.Exception)
     {
         LoadResult.Error(e)
     }
    }

}