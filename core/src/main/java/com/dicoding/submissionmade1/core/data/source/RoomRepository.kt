package com.dicoding.submissionmade1.core.data.source

import com.dicoding.submissionmade1.core.data.source.local.RoomDataSource
import com.dicoding.submissionmade1.core.domain.model.Favorite
import com.dicoding.submissionmade1.core.domain.repository.IRoomRepository
import com.dicoding.submissionmade1.core.utils.FavoriteMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomRepository(private val roomDataSource: RoomDataSource) : IRoomRepository {

    override suspend fun insertFavorite(favorite: Favorite) {
        val entity = FavoriteMapper.mapDomainToEntity(favorite)
        roomDataSource.insert(entity)
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        val entity = FavoriteMapper.mapDomainToEntity(favorite)
        roomDataSource.delete(entity)
    }

    override fun getFavoriteById(id: String): Flow<Favorite?> {
        return roomDataSource.getFavoriteById(id).map { entity ->
            entity?.let { FavoriteMapper.mapEntityToDomain(it) }
        }
    }

    override fun getAllFavorites(): Flow<List<Favorite>> {
        return roomDataSource.getAllFavoriteEvents()
            .map { entities -> FavoriteMapper.mapEntitiesToDomain(entities) }
    }
}