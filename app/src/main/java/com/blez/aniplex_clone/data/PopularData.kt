package com.blez.aniplex_clone.data




data class PopularModelItem(
    val animeId: String,
    val animeImg: String,
    val animeTitle: String,
    val animeUrl: String,
    val releasedDate: String
)
data class PopularDataModel(
    val currentPage: Int,
    val hasNextPage: Boolean,
    val results: List<Result>
)

 data class Result(
    val genres: List<String>,
    val id: String,
    val image: String,
    val title: String,
    val url: String
){
    fun toPopularModelItem() : PopularModelItem{
        return PopularModelItem(
            animeId = id,
            animeImg = image,
            animeTitle = title,
            animeUrl = url,
            releasedDate = genres.iterator().toString()
        )
    }
}