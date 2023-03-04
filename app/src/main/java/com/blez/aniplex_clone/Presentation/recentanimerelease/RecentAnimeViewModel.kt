package com.blez.aniplex_clone.Presentation.recentanimerelease

import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.blez.aniplex_clone.data.RecentData
import com.blez.aniplex_clone.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class RecentAnimeViewModel @Inject constructor(private val animeRepository: AnimeRepository) : ViewModel() {
    sealed class RecentEvent{
        object Loading : RecentEvent()
        data class RecentPaging(val list : Flow<PagingData<RecentData>>): RecentEvent()
        }

   private val _pagingData = MutableStateFlow<RecentEvent>(RecentEvent.Loading)
    val pagingData : StateFlow<RecentEvent>
    get() = _pagingData

    val popularList = animeRepository.getPopularAnimePager().flow.cachedIn(viewModelScope)
    val topAiringList = animeRepository.getTopAiringPager().flow.cachedIn(viewModelScope)
    val movieDataList = animeRepository.getMoviePager().flow.cachedIn(viewModelScope)

 suspend   fun getVideoLink(episodeID : String) = animeRepository.getVideoData(episodeID)

    fun getSearchData(animeQuery : String) = animeRepository.getSearchData(animeQuery)


    fun getRecentPagingData()  {
      val list =  animeRepository.getReceentRelease().flow.cachedIn(viewModelScope)
        _pagingData.value = RecentEvent.RecentPaging(list)

    }







}