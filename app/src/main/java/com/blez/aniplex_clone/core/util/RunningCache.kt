package com.blez.aniplex_clone.core.util

import com.blez.aniplex_clone.data.AnimeDetails

import com.blez.aniplex_clone.data.RecentData
import com.blez.aniplex_clone.data.ReleaseAnimes
import com.blez.aniplex_clone.data.SearchAnime
import com.blez.aniplex_clone.data.SearchAnimeItem
import com.blez.aniplex_clone.data.VideoData
import com.blez.aniplex_clone.data.VideoFormat

object RunningCache {

    val animeDetails = mutableMapOf<String, AnimeDetails?>()
    val recentAnimeCaching = mutableMapOf<Int, List<RecentData>?>()
    val videoLinkCaching = mutableMapOf<String, VideoFormat?>()
    val searchResultCaching = mutableMapOf<String, SearchAnime?>()
    val recommendationCaching = mutableMapOf<Int, List<SearchAnimeItem>?>()
}