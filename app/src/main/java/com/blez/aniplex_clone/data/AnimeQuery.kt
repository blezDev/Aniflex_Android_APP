package com.blez.aniplex_clone.data

import com.google.gson.annotations.SerializedName

data class AnimeQuery(
    @SerializedName("anime")
    val anime: String
)
