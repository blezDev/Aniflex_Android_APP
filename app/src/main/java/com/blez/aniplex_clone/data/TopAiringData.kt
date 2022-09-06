package com.blez.aniplex_clone.data

class TopAiringData : ArrayList<TopAiringDataItem>()

data class TopAiringDataItem(
    val animeId: String,
    val animeImg: String,
    val animeTitle: String,
    val animeUrl: String,
    val genres: List<String>,
    val latestEp: String
)