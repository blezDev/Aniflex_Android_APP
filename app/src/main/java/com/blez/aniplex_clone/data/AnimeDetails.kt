package com.blez.aniplex_clone.data


data class Episodes(
    val episodeId: String,
    val episodeNum: String,
    val episodeUrl: String
)

data class DetailModel(
    val animeImg: String ?= null,
    val animeTitle: String?= null,
    val episodesList: List<Episodes>?= null,
    val genres: List<String>?= null,
    val otherNames: String?= null,
    val releasedDate: String?= null,
    val status: String?= null,
    val synopsis: String?= null,
    val totalEpisodes: String?= null,
    val type: String?= null
)


data class AnimeDetails(
    val description: String,
    val episodes: List<Episode>,
    val genres: List<String>,
    val id: String,
    val image: String,
    val otherName: String,
    val releaseDate: String,
    val status: String,
    val subOrDub: String,
    val title: String,
    val totalEpisodes: Int,
    val type: String,
    val url: String
){
    fun toDetailModel() : DetailModel{
        return  DetailModel(
            animeImg = image,
            animeTitle = title,
            episodesList = episodes.map { it.toEpisodeList() },
            genres = genres,
            otherNames = otherName,
            releasedDate = releaseDate,
            status = status,
            synopsis = description,
            totalEpisodes = totalEpisodes.toString(),
            type = type

        )
    }
}


data class Episode(
    val id: String,
    val number: Int,
    val url: String
){
    fun toEpisodeList() : Episodes{
        return Episodes(
            episodeId = id,
            episodeUrl = url,
            episodeNum = number.toString()
        )
    }
}