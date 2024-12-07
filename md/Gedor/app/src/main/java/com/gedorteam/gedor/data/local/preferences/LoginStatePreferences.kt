package com.gedorteam.gedor.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
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