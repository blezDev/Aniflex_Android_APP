package com.blez.aniplex_clone.Presentation.detailsAnime

import androidx.lifecycle.ViewModel
import com.blez.aniplex_clone.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val animeRepository: AnimeRepository) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAnimeDetails(animeId : String) = animeRepository.getAnimeDetails(animeId)

    fun getVideoLink(episodeID : String) = animeRepository.getVideoData(episodeID)


}