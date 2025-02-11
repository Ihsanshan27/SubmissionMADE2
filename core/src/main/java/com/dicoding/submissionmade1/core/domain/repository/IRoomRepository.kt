package com.dicoding.submissionmade1.core.domain.repository

import com.dicoding.submissionmade1.core.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface IRoomRepository {
    suspend fun insertFavorite(favorite: Favorite)

    suspend fun deleteFavorite(favorite: Favorite)

    fun getFavoriteById(id: String): Flow<Favorite?>

    fun getAllFavorites(): Flow<List<Favorite>>
}