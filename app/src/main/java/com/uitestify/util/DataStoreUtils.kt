package com.uitestify.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val DARK_MODE_ENABLED = booleanPreferencesKey("dark_mode_enabled")
    val SAVED_EMAIL = stringPreferencesKey("saved_email")
    val SAVED_PASSWORD = stringPreferencesKey("saved_password")
}
