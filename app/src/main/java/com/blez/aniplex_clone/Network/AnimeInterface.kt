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
    @GET("/anime/gogoanime/recent-episodes")
    suspend fun getRecentRelease(@Query("page") page : Int) : Response<RecentReleaseModel>
    @GET("/anime/gogoanime/watch/{episodeId}")
    suspend fun getVideoLink(@Path(value = "episodeId")episodeId : String) : Response<VideoFormat>
    @GET("/anime-movies")
    suspend fun getMoviesList(@Query("page") page : Int) : Response<MovieData>
    @GET("/anime/gogoanime/top-airing")
    suspend fun getPopularAnime(@Query("page") page : Int) : Response<PopularDataModel>
    @GET("/top-airing")
    suspend fun getTopAiring(@Query("page") page: Int) : Response<TopAiringData>
    @GET("/anime/gogoanime/info/{animeId}")
    suspend fun getAnimeDetails(@Path(value = "animeId")animeId : String ) : Response<AnimeDetails>
    @GET("/anime/gogoanime/{query}")
    suspend fun getAnimeSearch(@Path("query")animeSearch : String ) : Response<SearchAnime>

    @GET("/download")
    suspend fun getDownloadLink(@Header("downloadLink") downloadLink : String)


    @POST("/predict")
    suspend fun getRecommendation(@Body anime : AnimeQuery) : Response<List<SearchAnimeItem>>




}