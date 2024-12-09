package com.gedorteam.gedor.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gedorteam.gedor.data.local.preferences.LoginStatePreference
import kotlinx.coroutines.launch

class SettingsViewModel(private val loginStatePreference: LoginStatePreference) : ViewModel() {

    fun getUserIDSync(): LiveData<String?> {
        return loginStatePreference.getUserID().asLiveData()
    }

    fun clearPreferences() {
        viewModelScope.launch {
            loginStatePreference.clearLoginState()
        }
    }
}