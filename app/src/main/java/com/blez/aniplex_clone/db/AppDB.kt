package com.blez.aniplex_clone.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blez.aniplex_clone.db.dao.WatchedDao

@Database(entities = [WatHistory::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    abstract fun watchedDao() : WatchedDao
}