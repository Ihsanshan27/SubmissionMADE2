package com.dicoding.submissionmade1.core.utils

import com.dicoding.submissionmade1.core.data.source.local.entity.FavoriteEntity
import com.dicoding.submissionmade1.core.domain.model.Favorite

object FavoriteMapper {
    fun mapEntityToDomain(input: FavoriteEntity): Favorite {
        return Favorite(
            id = input.id,
            name = input.name,
            mediaCover = input.mediaCover,
            cityName = input.cityName
        )
    }

    fun mapDomainToEntity(input: Favorite): FavoriteEntity {
        return FavoriteEntity(
            id = input.id,
            name = input.name,
            mediaCover = input.mediaCover,
            cityName = input.cityName
        )
    }

    fun mapEntitiesToDomain(input: List<FavoriteEntity>): List<Favorite> {
        return input.map { mapEntityToDomain(it) }
    }
}