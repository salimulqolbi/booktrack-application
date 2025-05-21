package com.example.booktrackapplication.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.booktrack.data.response.UserResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class DataStoreManager(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("user_token")
        private val USER_JSON_KEY = stringPreferencesKey("user_data")
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun getToken(): String? {
        return dataStore.data
            .map { preferences -> preferences[TOKEN_KEY] }
            .firstOrNull()
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun saveUser(user: UserResponse) {
        val json = Gson().toJson(user)
        dataStore.edit { preferences ->
            preferences[USER_JSON_KEY] = json
        }
    }

    suspend fun getUser(): UserResponse? {
        return dataStore.data
            .map { it[USER_JSON_KEY] }
            .firstOrNull()
            ?.let { json -> Gson().fromJson(json, UserResponse::class.java) }
    }

    suspend fun clearUser() {
        dataStore.edit { it.remove(USER_JSON_KEY) }
    }
}