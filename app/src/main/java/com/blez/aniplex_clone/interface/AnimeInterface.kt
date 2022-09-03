package com.blez.aniplex_clone.`interface`

import com.blez.aniplex_clone.data.ReleaseAnimes
import retrofit2.Response
import retrofit2.http.GET

interface AnimeInterface {
    @GET("/recent-release")
    suspend fun getRecentRelease() : Response<ReleaseAnimes>


}