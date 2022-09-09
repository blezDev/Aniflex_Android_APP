package com.blez.aniplex_clone.data

data class AnimeDetails(
    val animeImg: String,
    val animeTitle: String,
    val episodesList: List<Episodes>,
    val genres: List<String>,
    val otherNames: String,
    val releasedDate: String,
    val status: String,
    val synopsis: String,
    val totalEpisodes: String,
    val type: String
)

data class Episodes(
    val episodeId: String,
    val episodeNum: String,
    val episodeUrl: String
)