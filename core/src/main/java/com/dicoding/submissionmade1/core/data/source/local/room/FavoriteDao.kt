package com.dicoding.submissionmade1.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.submissionmade1.core.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Delete
    suspend fun delete(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorite_event WHERE id = :id")
    fun getFavoriteEventById(id: String): Flow<FavoriteEntity>

    @Query("SELECT * FROM favorite_event")
    fun getAllFavoriteEvents(): Flow<List<FavoriteEntity>>
}