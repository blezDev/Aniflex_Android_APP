package com.blez.aniplex_clone.paging.moviePaging

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blez.aniplex_clone.Network.AnimeInterface
import com.blez.aniplex_clone.data.MovieDataItem

class MovePagingDataSource(private val animeAPI : AnimeInterface): PagingSource<Int, MovieDataItem>() {
    override fun getRefreshKey(state: PagingState<Int, MovieDataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDataItem> {
      val position = params.key ?:1
        val response = animeAPI.getMoviesList(position).body()
        return try {
            LoadResult.Page(
                data = response!!,
                prevKey = if(position==1) null else position - 1,
                nextKey = position + 1
            )
        }catch (e : Exception)
        {
            LoadResult.Error(e)
        }
    }
}