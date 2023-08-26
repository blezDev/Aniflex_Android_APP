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

data class RecentReleaseModel(
    val currentPage: Int,
    val hasNextPage: Boolean,
    @SerializedName("results")
    val results: List<ResultPopular>
)

data class ResultPopular(
    val episodeId: String,
    val episodeNumber: Int,
    val id: String,
    val image: String,
    val title: String,
    val url: String
) {
    fun toRecentModelItem(): RecentData {
        return RecentData(
            animeId = id,
            animeImg = image,
            animeTitle = title,
            episodeId = episodeId,
            episodeNum = episodeNumber.toString(),
            episodeUrl = url,
            subOrDub = "NA"
        )
    }
}