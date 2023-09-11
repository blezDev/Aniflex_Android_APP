package com.blez.aniplex_clone.core.util

import com.blez.aniplex_clone.data.AnimeDetails
import com.blez.aniplex_clone.data.PopularModelItem

import com.blez.aniplex_clone.data.RecentData
import com.blez.aniplex_clone.data.ReleaseAnimes
import com.blez.aniplex_clone.data.SearchAnime
import com.blez.aniplex_clone.data.SearchAnimeItem
import com.blez.aniplex_clone.data.VideoData
import com.blez.aniplex_clone.data.VideoFormat

object RunningCache {

    val animeDetails = mutableMapOf<String, AnimeDetails?>()
    val recentAnimeCaching = mutableMapOf<Int, List<RecentData>?>()
    val popularAnimeCaching = mutableMapOf<Int, List<PopularModelItem>>()
    val videoLinkCaching = mutableMapOf<String, VideoFormat?>()
    val searchResultCaching = mutableMapOf<String, List<SearchAnimeItem>?>()
    val recommendationCaching = mutableMapOf<String, List<SearchAnimeItem>?>()

}

