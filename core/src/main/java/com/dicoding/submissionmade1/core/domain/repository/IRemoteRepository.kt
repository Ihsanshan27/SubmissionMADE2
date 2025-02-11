package com.dicoding.submissionmade1.core.domain.repository

import com.dicoding.submissionmade1.core.domain.model.Events
import kotlinx.coroutines.flow.Flow

interface IRemoteRepository {
    fun getListEvents(query: Int): Flow<List<Events>>

    fun searchEvents(active: Int, query: String): Flow<List<Events>>

    fun getDetailEvent(id: Int): Flow<Events?>

    suspend fun getDailyReminder(active: Int, limit: Int): List<Events>
}