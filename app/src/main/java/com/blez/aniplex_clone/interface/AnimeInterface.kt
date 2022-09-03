package com.blez.aniplex_clone.`interface`

import com.blez.aniplex_clone.data.ReleaseAnimes
import com.blez.aniplex_clone.data.Source
import com.blez.aniplex_clone.data.VideoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeInterface {
    @GET("/recent-release")
    suspend fun getRecentRelease() : Response<ReleaseAnimes>
    @GET("/vidcdn/watch/{episodeId}")
    suspend fun getVideoLink(@Path(value = "episodeId")episodeId : String) : Response<VideoData>



}