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
    val retService = RetrofitInstance.getRetrofitInstance().create(AnimeInterface::class.java)
    var responseLiveData : LiveData<Response<ReleaseAnimes>> = liveData {
        emit(retService.getRecentRelease(page))
    }



    fun increment(){
        page++

    }
    fun decrement(){
        if(page<2){
            page = 1

        }
        else {
            page--

        }

    }

}