package com.example.chapter_5.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context) {

    private val dataStore: DataStore<Preferences> = context.applicationContext.dataStore

    companion object {
        @Volatile
        private var INSTANCE: DataStoreManager? = null

        fun getInstance(context: Context): DataStoreManager {
            return INSTANCE ?: synchronized(this) {
                val instance = DataStoreManager(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }

    private val USERNAME_KEY = stringPreferencesKey("username")
    private val EMAIL_KEY = stringPreferencesKey("email")
    private val PASSWORD_KEY = stringPreferencesKey("password")
    private val IS_LOGIN_KEY = booleanPreferencesKey("is_login")
    private val USER_PHOTO_KEY = stringPreferencesKey("user_photo")

    val username: Flow<String?> = dataStore.data.map { preferences ->
        preferences[USERNAME_KEY]
    }

    val email: Flow<String?> = dataStore.data.map { preferences ->
        preferences[EMAIL_KEY]
    }

    val password: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PASSWORD_KEY]
    }

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_LOGIN_KEY] ?: false
    }

    suspend fun saveUser(username: String, email: String, password: String) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
            preferences[EMAIL_KEY] = email
            preferences[PASSWORD_KEY] = password
            preferences[IS_LOGIN_KEY] = true
        }
    }
    suspend fun clearUser() {
        dataStore.edit { preferences ->
            preferences[IS_LOGIN_KEY] = false
        }
    }

    suspend fun loggedInUser() {
        dataStore.edit { preferences ->
            preferences[IS_LOGIN_KEY] = true
        }
    }

    suspend fun saveUserPhoto(photoPath: String) {
        dataStore.edit { preferences ->
            preferences[USER_PHOTO_KEY] = photoPath
        }
    }

    suspend fun getUserPhoto(): String? {
        val preferences = dataStore.data.first()
        return preferences[USER_PHOTO_KEY]
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_pref")
