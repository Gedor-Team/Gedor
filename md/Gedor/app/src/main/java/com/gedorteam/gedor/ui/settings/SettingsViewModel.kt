package com.gedorteam.gedor.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gedorteam.gedor.data.local.preferences.LoginStatePreference
import com.gedorteam.gedor.data.local.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val loginStatePreference: LoginStatePreference) : ViewModel() {

    private val _userPreferences = MutableStateFlow<UserPreferences?>(null)
    val userPreferences: StateFlow<UserPreferences?> = _userPreferences

    fun getUserIDSync(): LiveData<String?> {
        return loginStatePreference.getUserID().asLiveData()
    }

    fun clearPreferences() {
        viewModelScope.launch {
            loginStatePreference.clearLoginState()
        }
    }

    fun getUserPreferences() {
        viewModelScope.launch {
            loginStatePreference.getUserPreferences().collect { preferences ->
                _userPreferences.value = preferences
            }
        }
    }
}