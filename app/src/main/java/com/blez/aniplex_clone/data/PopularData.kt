package com.blez.aniplex_clone.data

class PopularData : ArrayList<PopularDataItem>()

data class PopularDataItem(
    val animeId: String,
    val animeImg: String,
    val animeTitle: String,
    val animeUrl: String,
    val releasedDate: String
)