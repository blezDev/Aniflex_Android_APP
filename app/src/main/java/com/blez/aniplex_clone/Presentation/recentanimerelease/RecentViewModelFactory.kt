package com.blez.aniplex_clone.Presentation.recentanimerelease

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class RecentViewModelFactory() : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecentAnimeViewModel::class.java)){
            return RecentAnimeViewModel() as T
        }
        throw  IllegalArgumentException("ViewModel Not Found")
    }
}