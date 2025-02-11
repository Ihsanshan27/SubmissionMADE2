package com.dicoding.submissionmade1.core.data.source.local

import com.dicoding.submissionmade1.core.data.source.local.entity.FavoriteEntity
import com.dicoding.submissionmade1.core.data.source.local.room.FavoriteDao
import kotlinx.coroutines.flow.Flow

class RoomDataSource(private val favoriteDao: FavoriteDao) {
    suspend fun insert(favorite: FavoriteEntity) {
        favoriteDao.insert(favorite)
    }

    suspend fun delete(favorite: FavoriteEntity) {
        favoriteDao.delete(favorite)
    }

    fun getFavoriteById(id: String): Flow<FavoriteEntity?> {
        return favoriteDao.getFavoriteEventById(id)
    }

    fun getAllFavoriteEvents(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getAllFavoriteEvents()
    }
}