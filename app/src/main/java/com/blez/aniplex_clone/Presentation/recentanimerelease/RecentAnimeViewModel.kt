package com.blez.aniplex_clone.Presentation.recentanimerelease

import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.blez.aniplex_clone.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class RecentAnimeViewModel @Inject constructor(private val animeRepository: AnimeRepository) : ViewModel() {

    val list = animeRepository.getReceentRelease().flow.cachedIn(viewModelScope)
    val popularList = animeRepository.getPopularAnimePager().flow.cachedIn(viewModelScope)
    val topAiringList = animeRepository.getTopAiringPager().flow.cachedIn(viewModelScope)
    val movieDataList = animeRepository.getMoviePager().flow.cachedIn(viewModelScope)

 suspend   fun getVideoLink(episodeID : String) = animeRepository.getVideoData(episodeID)

    fun getSearchData(animeQuery : String) = animeRepository.getSearchData(animeQuery)







}