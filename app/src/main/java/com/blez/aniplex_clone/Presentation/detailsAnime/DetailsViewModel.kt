package com.blez.aniplex_clone.Presentation.detailsAnime

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.blez.aniplex_clone.`interface`.AnimeInterface
import com.blez.aniplex_clone.data.AnimeDetails
import com.blez.aniplex_clone.data.ReleaseAnimes
import com.blez.aniplex_clone.network.RetrofitInstance
import retrofit2.Response

class DetailsViewModel(val animeId : String) : ViewModel() {
    val retService = RetrofitInstance.getRetrofitInstance()
        .create(AnimeInterface::class.java)
    var responseLiveData : LiveData<Response<AnimeDetails>> = liveData {
        val response = retService.getAnimeDetails(animeId)
        emit(response)
    }
}