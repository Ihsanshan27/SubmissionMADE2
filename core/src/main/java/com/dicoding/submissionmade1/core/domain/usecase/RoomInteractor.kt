package com.dicoding.submissionmade1.core.domain.usecase

import com.dicoding.submissionmade1.core.domain.model.Favorite
import com.dicoding.submissionmade1.core.domain.repository.IRoomRepository
import kotlinx.coroutines.flow.Flow

class RoomInteractor(private val repository: IRoomRepository) : RoomUseCase {
    override suspend fun insertFavorite(favorite: Favorite) {
        repository.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        repository.deleteFavorite(favorite)
    }

    override fun getFavoriteById(id: String): Flow<Favorite?> {
        return repository.getFavoriteById(id)
    }

    override fun getAllFavorites(): Flow<List<Favorite>> {
        return repository.getAllFavorites()
    }
}