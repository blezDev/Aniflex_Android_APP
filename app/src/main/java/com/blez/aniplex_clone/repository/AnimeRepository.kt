package com.blez.aniplex_clone.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.blez.aniplex_clone.`interface`.AnimeInterface
import com.blez.aniplex_clone.paging.PagingDataSource

class AnimeRepository(private val animeInterface: AnimeInterface) {
    fun getRecentAnime() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = {PagingDataSource(animeInterface)}

    ).liveData
}