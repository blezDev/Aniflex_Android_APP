package com.blez.aniplex_clone.Presentation.detailsAnime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException


@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private var animeId : String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)){
            return DetailsViewModel(animeId) as T
        }
        throw  IllegalArgumentException("ViewModel Not Found")
    }
}