package com.blez.aniplex_clone.data

import com.google.gson.annotations.SerializedName

data class RecentData(
    @SerializedName("animeId")
    val animeId : String,
    @SerializedName("episodeId")
    val episodeId : String,
    @SerializedName("animeTitle")
    val animeTitle : String,
    @SerializedName("episodeNum")
    val episodeNum : String,
    @SerializedName("subOrDub")
    val subOrDub : String,
    @SerializedName("animeImg")
    val animeImg : String,
    @SerializedName("episodeUrl")
    val episodeUrl : String,

)