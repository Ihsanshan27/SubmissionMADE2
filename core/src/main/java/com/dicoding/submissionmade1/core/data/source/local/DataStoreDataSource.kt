package com.dicoding.submissionmade1.core.data.source.local

import com.dicoding.submissionmade1.core.data.source.local.datastore.SettingsPreferences
import kotlinx.coroutines.flow.Flow

class DataStoreDataSource(private val settingPreferences: SettingsPreferences) {
    fun getThemeSetting(): Flow<Boolean> {
        return settingPreferences.getThemeSetting()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        settingPreferences.saveThemeSetting(isDarkModeActive)
    }

    fun getNotificationSetting(): Flow<Boolean> {
        return settingPreferences.getNotificationSetting()
    }

    suspend fun saveNotificationSetting(isNotificationActive: Boolean) {
        settingPreferences.saveNotificationSetting(isNotificationActive)
    }
}