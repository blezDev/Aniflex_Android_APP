package com.blez.aniplex_clone.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.blez.aniplex_clone.Network.AnimeInterface
import com.blez.aniplex_clone.paging.PagingDataSource
import kotlinx.coroutines.*
import javax.inject.Inject

class AnimeRepository @Inject constructor( val animeAPI: AnimeInterface) {

    fun getReceentRelease() = Pager(
        config = PagingConfig(pageSize = 20,enablePlaceholders = false),
        pagingSourceFactory = {PagingDataSource(animeAPI)}

    )



    fun getSearchData(animeQuery : String) = CoroutineScope(Dispatchers.Main).async { animeAPI.getAnimeSearch(animeSearch = animeQuery).body() }

    fun getVideoData(episodeID : String)  = CoroutineScope(Dispatchers.Main).async{ animeAPI.getVideoLink(episodeID).body()}


    fun getAnimeDetails(animeId : String) =  CoroutineScope(Dispatchers.Main).async{ animeAPI.getAnimeDetails(animeId).body()}

}