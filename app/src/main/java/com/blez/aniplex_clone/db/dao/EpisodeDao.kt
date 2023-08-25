package com.blez.aniplex_clone.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blez.aniplex_clone.db.WatHistory
import com.blez.aniplex_clone.db.model.EpisodeHistory
import com.blez.aniplex_clone.utils.Constants
@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodeNumber(history: EpisodeHistory)


}