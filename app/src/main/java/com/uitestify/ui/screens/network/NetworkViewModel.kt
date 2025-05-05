package com.uitestify.ui.screens.network

import android.app.Application
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import com.uitestify.util.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NetworkViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = application.dataStore
    private val AUTO_REFRESH_KEY = booleanPreferencesKey("auto_refresh")

    val autoRefresh: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[AUTO_REFRESH_KEY] == true
    }

    suspend fun setAutoRefresh(value: Boolean) {
        dataStore.edit { it[AUTO_REFRESH_KEY] = value }
    }
}
