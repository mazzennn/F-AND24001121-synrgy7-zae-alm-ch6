package com.example.chapter_5.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(val context: Context) {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_pref")
    val USERNAME_KEY = stringPreferencesKey("username")
    val EMAIL_KEY = stringPreferencesKey("email")
    val PASSWORD_KEY = stringPreferencesKey("password")


    val username : Flow<String?> = context.dataStore.data.map{preferences->
        preferences[USERNAME_KEY]
    }
    val email : Flow<String?> = context.dataStore.data.map{preferences->
        preferences[EMAIL_KEY]
    }
    val password : Flow<String?> = context.dataStore.data.map{preferences->
        preferences[PASSWORD_KEY]
    }

    suspend fun saveUser(username: String, email: String, password: String){
        context.dataStore.edit{preferences->
            preferences[USERNAME_KEY] = username
            preferences[EMAIL_KEY] = email
            preferences[PASSWORD_KEY] = password
        }
    }

}