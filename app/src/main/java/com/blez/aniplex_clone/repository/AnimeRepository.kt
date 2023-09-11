package com.blez.aniplex_clone.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.blez.aniplex_clone.Network.AnimeInterface
import com.blez.aniplex_clone.core.util.RunningCache
import com.blez.aniplex_clone.core.util.RunningCache.recommendationCaching
import com.blez.aniplex_clone.core.util.RunningCache.searchResultCaching
import com.blez.aniplex_clone.core.util.RunningCache.videoLinkCaching
import com.blez.aniplex_clone.data.AnimeDetails
import com.blez.aniplex_clone.data.AnimeQuery
import com.blez.aniplex_clone.data.SearchAnimeItem
import com.blez.aniplex_clone.data.VideoFormat
import com.blez.aniplex_clone.db.WatHistory
import com.blez.aniplex_clone.db.dao.WatchedDao
import com.blez.aniplex_clone.paging.PagingDataSource
import com.blez.aniplex_clone.paging.moviePaging.MovePagingDataSource
import com.blez.aniplex_clone.paging.popular.PopularPagingData
import com.blez.aniplex_clone.paging.topPaging.TopAiringPagingDataSource
import com.blez.aniplex_clone.utils.ResultState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnimeRepository @Inject constructor(val animeAPI: AnimeInterface, val dao: WatchedDao) {

    fun getReceentRelease() = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { PagingDataSource(animeAPI) }

    )

    fun getPopularAnimePager() = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { PopularPagingData(animeAPI) }
    )

    fun getTopAiringPager() = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { TopAiringPagingDataSource(animeAPI) }
    )

    fun getMoviePager() = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { MovePagingDataSource(animeAPI) }
    )


    suspend fun getSearchData(animeQuery: String): List<SearchAnimeItem>? {
        return if (searchResultCaching[animeQuery] != null) {
             searchResultCaching[animeQuery]
        } else {
            val result = animeAPI.getAnimeSearch(animeSearch = animeQuery).body()
            searchResultCaching[animeQuery] = result?.results?.map { it.toSearchAnimeItem() }
            searchResultCaching[animeQuery]
        }
    }

    suspend fun insertDataIntoDB(history: WatHistory) {
        dao.insertDate(history)
    }

    suspend fun getHistoryData(): List<WatHistory> {
        return dao.getHistory()
    }

    suspend fun getVideoData(episodeID: String): VideoFormat? {
        return if (videoLinkCaching[episodeID] == null) {
            val result = animeAPI.getVideoLink(episodeID).body()
            videoLinkCaching[episodeID] = result
            result
        } else
            videoLinkCaching[episodeID]
    }

    fun getDownloadVideoLink(episodeID: String) =
        CoroutineScope(Dispatchers.IO).async { animeAPI.getVideoLink(episodeID).body() }


    suspend fun getAnimeDetails(animeId: String): AnimeDetails? {
        if (RunningCache.animeDetails[animeId] == null) {
            val result = animeAPI.getAnimeDetails(animeId).body()
            RunningCache.animeDetails[animeId] = result
            return result
        } else {
            return RunningCache.animeDetails[animeId]
        }

    }

    fun getRecentAnime() =
        CoroutineScope(Dispatchers.Main).async { animeAPI.getRecentRelease(1).body() }

    fun getPopularAnime() =
        CoroutineScope(Dispatchers.Main).async { animeAPI.getPopularAnime(1).body() }

    fun getTopAiring() = CoroutineScope(Dispatchers.Main).async { animeAPI.getTopAiring(1).body() }
    fun getMovieItems() =
        CoroutineScope(Dispatchers.Main).async { animeAPI.getMoviesList(1).body() }

    fun downloadLink(episodeUrl: String) =
        CoroutineScope(Dispatchers.IO).async { animeAPI.getDownloadLink(episodeUrl) }

    suspend fun insertWatchHistory(watHistory: WatHistory) = dao.insertDate(watHistory)


    suspend fun getRecommendation(
        anime: String,
        re_cache: Boolean = false
    ): ResultState<List<SearchAnimeItem>> {

        try {
            if (re_cache) {
                val result = animeAPI.getRecommendation(AnimeQuery(anime)).body()
                recommendationCaching[anime] = result?.map { it.toSearchAnimeItem() }
                return ResultState.Success(recommendationCaching[anime])
            }

            if (recommendationCaching[anime] != null) {
                return ResultState.Success(recommendationCaching[anime])
            }

            val result = animeAPI.getRecommendation(AnimeQuery(anime)).body()
            recommendationCaching[anime] = result?.map { it.toSearchAnimeItem() }
            return ResultState.Success(result?.map { it.toSearchAnimeItem() })


        } catch (e: Exception) {
            return ResultState.Error(e.message)
        }

    }


}