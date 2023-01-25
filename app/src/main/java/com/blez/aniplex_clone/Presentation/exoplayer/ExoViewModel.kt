package com.blez.aniplex_clone.Presentation.exoplayer

import androidx.lifecycle.ViewModel
import com.blez.aniplex_clone.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExoViewModel @Inject constructor(private val animeRepository: AnimeRepository) : ViewModel() {

    fun getVideoData(episodeLink : String) = animeRepository.getVideoData(episodeID = episodeLink)

}