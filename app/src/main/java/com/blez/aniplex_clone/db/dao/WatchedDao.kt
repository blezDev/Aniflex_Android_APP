package com.blez.aniplex_clone.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blez.aniplex_clone.db.WatHistory
import com.blez.aniplex_clone.utils.Constants
import com.blez.aniplex_clone.utils.Constants.DB_TABLE_NAME_WATCHED
import kotlinx.coroutines.flow.Flow
import javax.annotation.Nullable

@Dao
interface WatchedDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertDate(history: WatHistory)



}