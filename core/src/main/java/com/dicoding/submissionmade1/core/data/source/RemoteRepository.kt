package com.dicoding.submissionmade1.core.data.source

import com.dicoding.submissionmade1.core.data.source.remote.RemoteDataSource
import com.dicoding.submissionmade1.core.domain.model.Events
import com.dicoding.submissionmade1.core.domain.repository.IRemoteRepository
import com.dicoding.submissionmade1.core.utils.EventMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RemoteRepository(private val remoteDataSource: RemoteDataSource) : IRemoteRepository {

    override fun getListEvents(query: Int): Flow<List<Events>> {
        return remoteDataSource.getListEvents(query).map {
            EventMapper.mapEventsResponseToDomain(it)
        }
    }

    override fun searchEvents(active: Int, query: String): Flow<List<Events>> {
        return remoteDataSource.searchEvents(active, query).map {
            EventMapper.mapEventsResponseToDomain(it)
        }
    }

    override fun getDetailEvent(id: Int): Flow<Events> {
        return remoteDataSource.getDetailEvents(id).map {
            EventMapper.mapDetailEventResponseToDomain(it)
        }
    }

    override suspend fun getDailyReminder(active: Int, limit: Int): List<Events> {
        val response = remoteDataSource.getDailyReminder(active, limit)
        return EventMapper.mapEventsResponseToDomain(response)
    }
}
