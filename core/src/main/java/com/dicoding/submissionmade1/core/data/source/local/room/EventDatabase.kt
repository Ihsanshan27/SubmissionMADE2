package com.dicoding.submissionmade1.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.submissionmade1.core.data.source.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EventDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}