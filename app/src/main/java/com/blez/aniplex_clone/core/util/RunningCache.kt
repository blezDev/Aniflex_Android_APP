package com.blez.aniplex_clone.core.util

import com.blez.aniplex_clone.data.AnimeDetails
import com.blez.aniplex_clone.data.PopularDataItem
import com.blez.aniplex_clone.data.ReleaseAnimes
import com.blez.aniplex_clone.data.SearchAnime
import com.blez.aniplex_clone.data.SearchAnimeItem
import com.blez.aniplex_clone.data.VideoData

object RunningCache {

    val animeDetails = mutableMapOf<String, AnimeDetails?>()
    val recentAnimeCaching = mutableMapOf<Int, ReleaseAnimes?>()
    val videoLinkCaching = mutableMapOf<String, VideoData?>()
    val searchResultCaching = mutableMapOf<String, SearchAnime?>()
    val recommendationCaching = mutableMapOf<Int, List<SearchAnimeItem>?>()
}