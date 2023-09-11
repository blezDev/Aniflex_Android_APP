package com.blez.aniplex_clone.Presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blez.aniplex_clone.data.PopularModelItem
import com.blez.aniplex_clone.data.SearchAnimeItem
import com.blez.aniplex_clone.repository.AnimeRepository
import com.blez.aniplex_clone.utils.ResultState
import com.blez.aniplex_clone.utils.SettingManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val animeRepository: AnimeRepository, private val settingManager: SettingManager) : ViewModel() {

    sealed class RecommendationEvent {
        object Idle : RecommendationEvent()

        object Loading : RecommendationEvent()

        data class Success(val data: List<SearchAnimeItem>) : RecommendationEvent()

        data class Failure(val message: String) : RecommendationEvent()
    }

    private val _recommendationState = MutableStateFlow<RecommendationEvent>(RecommendationEvent.Idle)
    val recommendationState: StateFlow<RecommendationEvent>
        get() = _recommendationState



    fun getRecommendation(animeQuery: String) {
        viewModelScope.launch {
            val result = animeRepository.getRecommendation(animeQuery)
            when (result) {
                is ResultState.Error -> {
                    _recommendationState.emit(RecommendationEvent.Failure(result.message.toString()))
                }

                is ResultState.Loading -> {
                    _recommendationState.emit(RecommendationEvent.Loading)
                }

                is ResultState.Success -> {
                    _recommendationState.emit(
                        RecommendationEvent.Success(
                            result.data ?: emptyList()
                        )
                    )
                }
            }
        }
    }

}