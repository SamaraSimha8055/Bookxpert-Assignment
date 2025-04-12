package com.example.bookxperttest.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("settings")

object NotificationPreferenceManager {
    private val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")

    fun getNotificationsEnabled(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[NOTIFICATIONS_ENABLED] ?: true
        }
    }

    suspend fun setNotificationsEnabled(context: Context, enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[NOTIFICATIONS_ENABLED] = enabled
        }
    }
}
