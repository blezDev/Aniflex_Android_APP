package com.blez.aniplex_clone.Presentation.home

import androidx.lifecycle.ViewModel
import com.blez.aniplex_clone.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val animeRepository: AnimeRepository) : ViewModel() {

    fun getRecentRelease() = animeRepository.getRecentAnime()
    fun getPopularAnime() = animeRepository.getPopularAnime()
    fun getTopAiring() = animeRepository.getTopAiring()
    fun movieItens() = animeRepository.getMovieItems()

}