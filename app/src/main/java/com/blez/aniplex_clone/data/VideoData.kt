package com.blez.aniplex_clone.data

import com.google.gson.annotations.SerializedName

data class VideoData(
    @SerializedName("Referer")
    val Referer: String,
    @SerializedName("sources")
    val sources: List<Source>,
    @SerializedName("sources_bk")
    val sources_bk: List<SourcesBk>
)

data class Source(
    @SerializedName("file")
    val file: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("type")
    val type: String
)

data class SourcesBk(
    @SerializedName("file")
    val file: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("type")
    val type: String

)