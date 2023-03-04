package com.blez.aniplex_clone.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blez.aniplex_clone.utils.Constants.DB_TABLE_NAME_WATCHED

@Entity(tableName = DB_TABLE_NAME_WATCHED)
data class WatHistory(
    @PrimaryKey(autoGenerate = false)
        val idNum : Int = 1,
    val imgUrl : String,
      val animeId : String,
     val watchedEpiID : String,

)
