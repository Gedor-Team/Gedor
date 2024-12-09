package com.gedorteam.gedor.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login_state")

class LoginStatePreference(private val dataStore: DataStore<Preferences>) {
    suspend fun saveLoginState(userID: String, username: String, email: String, phoneNumber: String) {
        withContext(Dispatchers.IO) {
            dataStore.updateData { preferences ->
                preferences.toMutablePreferences().apply {
                    this[USER_ID_KEY] = userID
                    this[USERNAME_KEY] = username
                    this[EMAIL_KEY] = email
                    this[PHONE_NUMBER_KEY] = phoneNumber
                }
            }
        }
    }

    suspend fun clearLoginState() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    fun getUserID(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID_KEY]
        }
    }

    fun getUserIDSync(): String? {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[USER_ID_KEY]
            }.first()
        }
    }

    fun getUsername(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[USERNAME_KEY]
        }
    }

    fun getEmail(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[EMAIL_KEY]
        }
    }

    fun getPhoneNumber(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[PHONE_NUMBER_KEY]
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: LoginStatePreference? = null

        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PHONE_NUMBER_KEY = stringPreferencesKey("phone_number")

        fun getInstance(dataStore: DataStore<Preferences>): LoginStatePreference {
            return INSTANCE ?: synchronized(this) {
                val instance = LoginStatePreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}