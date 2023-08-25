package com.blez.aniplex_clone.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blez.aniplex_clone.utils.Constants

@Entity(tableName = "Watch_Table")
data class EpisodeHistory(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val episodeNum : String,
    val animeId : String
)
