package com.blez.aniplex_clone.data

class SearchAnime : ArrayList<SearchAnimeItem>()

data class SearchAnimeItem(
    val animeId: String,
    val animeImg: String,
    val animeTitle: String,
    val animeUrl: String,
    val status: String
)

data class SearchDataModel(
    val currentPage: Int,
    val hasNextPage: Boolean,
    val results: List<Result23>
)

data class Result23(
    val id: String,
    val image: String,
    val releaseDate: String,
    val subOrDub: String,
    val title: String,
    val url: String
){
    fun toSearchAnimeItem() : SearchAnimeItem{
        return SearchAnimeItem(
            animeId = id,
            animeImg = image,
            animeTitle = title,
            animeUrl = url,
            status = releaseDate
        )
    }
}