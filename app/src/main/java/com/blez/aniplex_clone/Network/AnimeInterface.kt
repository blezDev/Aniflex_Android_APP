package com.blez.aniplex_clone.Network

import com.blez.aniplex_clone.data.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeInterface {
    @GET("/recent-release")
    suspend fun getRecentRelease(@Query("page") page : Int) : Response<ReleaseAnimes>
    @GET("/vidcdn/watch/{episodeId}")
    suspend fun getVideoLink(@Path(value = "episodeId")episodeId : String) : Response<VideoData>
    @GET("/anime-movies")
    suspend fun getMoviesList(@Query("page") page : Int) : Response<MovieData>
    @GET("/popular")
    suspend fun getPopularAnime(@Query("page") page : Int) : Response<PopularData>
    @GET("/top-airing")
    suspend fun getTopAiring(@Query("page") page: Int) : Response<TopAiringData>
    @GET("/anime-details/{animeId}")
    suspend fun getAnimeDetails(@Path(value = "animeId")animeId : String ) : Response<AnimeDetails>
    @GET("/search")
    suspend fun getAnimeSearch(@Query("keyw")animeSearch : String ) : Response<SearchAnime>

    @GET("/download")
    suspend fun getDownloadLink(@Header("downloadLink") downloadLink : String)


    @POST("/predict")
    suspend fun getRecommendation(@Body anime : AnimeQuery) : Response<List<SearchAnimeItem>>




}