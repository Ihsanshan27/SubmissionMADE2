package com.dicoding.submissionmade1.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface IDataStoreRepository {
    
    fun getThemeSettings(): Flow<Boolean>

    suspend fun saveThemeSetting(isDarkModeActive: Boolean)

    fun getNotificationSettings(): Flow<Boolean>

    suspend fun saveNotificationSetting(isNotificationActive: Boolean)

}