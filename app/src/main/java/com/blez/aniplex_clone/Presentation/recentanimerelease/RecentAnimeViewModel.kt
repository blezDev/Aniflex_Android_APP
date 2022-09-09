package com.blez.aniplex_clone.Presentation.recentanimerelease

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.blez.aniplex_clone.`interface`.AnimeInterface
import com.blez.aniplex_clone.data.ReleaseAnimes
import com.blez.aniplex_clone.network.RetrofitInstance
import retrofit2.Response

class RecentAnimeViewModel : ViewModel() {
    var page = 1
    val retService = RetrofitInstance.getRetrofitInstance()
        .create(AnimeInterface::class.java)
    val responseLiveData : LiveData<Response<ReleaseAnimes>> = liveData {
        val response = retService.getRecentRelease(page)
        emit(response)
    }
    fun increment(){
        page++
        responseLiveData
    }
    fun decrement(){
        if(page<2){
            page = 1
            responseLiveData
        }
        else {
            page--
            responseLiveData
        }

    }
}