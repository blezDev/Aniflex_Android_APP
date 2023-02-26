package com.blez.aniplex_clone.Presentation.detailsAnime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blez.aniplex_clone.data.AnimeDetails
import com.blez.aniplex_clone.db.WatHistory
import com.blez.aniplex_clone.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val animeRepository: AnimeRepository) : ViewModel() {

    sealed class SetupEvent{
        object AnimeDetailsLoading : SetupEvent()
        data class AnimeDetailsData(val data : AnimeDetails) : SetupEvent()
    }

    private val _details = MutableStateFlow<SetupEvent>(SetupEvent.AnimeDetailsLoading)
    val detailsPhase : StateFlow<SetupEvent>
    get() = _details

    fun getAnimeDetails(animeId : String) = viewModelScope.launch(Dispatchers.Main) {
        val detail = animeRepository.getAnimeDetails(animeId) ?: return@launch

        _details.value = SetupEvent.AnimeDetailsData(detail)
  }






}