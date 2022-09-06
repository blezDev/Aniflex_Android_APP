package com.blez.aniplex_clone.data

class MovieData : ArrayList<MovieDataItem>()

data class MovieDataItem(
    val animeId: String,
    val animeImg: String,
    val animeTitle: String,
    val animeUrl: String,
    val releasedDate: String
)