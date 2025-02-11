package com.dicoding.submissionmade1.core.domain.usecase

import com.dicoding.submissionmade1.core.domain.repository.IDataStoreRepository
import kotlinx.coroutines.flow.Flow

class DataStoreInteractor(private val repository: IDataStoreRepository): DataStoreUseCase {

    override fun getThemeSettings(): Flow<Boolean> {
        return repository.getThemeSettings()
    }

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        repository.saveThemeSetting(isDarkModeActive)
    }

    override fun getNotificationSettings(): Flow<Boolean> {
        return repository.getNotificationSettings()
    }

    override suspend fun saveNotificationSetting(isNotificationActive: Boolean) {
        repository.saveNotificationSetting(isNotificationActive)
    }
}