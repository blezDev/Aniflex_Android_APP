package com.blez.aniplex_clone.data

class SearchAnime : ArrayList<SearchAnimeItem>()

data class SearchAnimeItem(
    val animeId: String,
    val animeImg: String,
    val animeTitle: String,
    val animeUrl: String,
    val status: String
)