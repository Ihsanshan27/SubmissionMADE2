package com.dicoding.submissionmade1.core.domain.usecase

import kotlinx.coroutines.flow.Flow

interface DataStoreUseCase {
    fun getThemeSettings(): Flow<Boolean>

    suspend fun saveThemeSetting(isDarkModeActive: Boolean)

    fun getNotificationSettings(): Flow<Boolean>

    suspend fun saveNotificationSetting(isNotificationActive: Boolean)

}