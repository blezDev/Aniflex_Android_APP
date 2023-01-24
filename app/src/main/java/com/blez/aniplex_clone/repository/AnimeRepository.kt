package com.blez.aniplex_clone.repository

import android.graphics.pdf.PdfDocument.Page
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.blez.aniplex_clone.Network.AnimeInterface
import com.blez.aniplex_clone.paging.PagingDataSource
import com.blez.aniplex_clone.paging.moviePaging.MovePagingDataSource
import com.blez.aniplex_clone.paging.popular.PopularPagingData
import com.blez.aniplex_clone.paging.topPaging.TopAiringPagingDataSource
import kotlinx.coroutines.*
import javax.inject.Inject

class AnimeRepository @Inject constructor( val animeAPI: AnimeInterface) {

    fun getReceentRelease() = Pager(
        config = PagingConfig(pageSize = 20,enablePlaceholders = false),
        pagingSourceFactory = {PagingDataSource(animeAPI)}

    )
    fun getPopularAnimePager() = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {PopularPagingData(animeAPI)}
    )

    fun getTopAiringPager() = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {TopAiringPagingDataSource(animeAPI)}
    )

    fun getMoviePager() = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {MovePagingDataSource(animeAPI)}
    )


    fun getSearchData(animeQuery : String) = CoroutineScope(Dispatchers.Main).async { animeAPI.getAnimeSearch(animeSearch = animeQuery).body() }

    fun getVideoData(episodeID : String)  = CoroutineScope(Dispatchers.Main).async{ animeAPI.getVideoLink(episodeID).body()}


    fun getAnimeDetails(animeId : String) =  CoroutineScope(Dispatchers.Main).async{ animeAPI.getAnimeDetails(animeId).body()}

    fun getRecentAnime() = CoroutineScope(Dispatchers.Main).async { animeAPI.getRecentRelease(1).body()}
    fun getPopularAnime() = CoroutineScope(Dispatchers.Main).async { animeAPI.getPopularAnime(1).body()}
    fun getTopAiring() = CoroutineScope(Dispatchers.Main).async { animeAPI.getTopAiring(1).body() }
    fun getMovieItems() = CoroutineScope(Dispatchers.Main).async { animeAPI.getMoviesList(1).body()}

}