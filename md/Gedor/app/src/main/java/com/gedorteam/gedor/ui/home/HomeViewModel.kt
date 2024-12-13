package com.gedorteam.gedor.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gedorteam.gedor.data.local.preferences.LoginStatePreference
import com.gedorteam.gedor.data.local.preferences.UserPreferences
import com.gedorteam.gedor.data.repositories.ComplaintRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val complaintRepository: ComplaintRepository,
    private val loginStatePreference: LoginStatePreference
) : ViewModel() {
    private val _userPreferences = MutableStateFlow<UserPreferences?>(null)
    val userPreferences: StateFlow<UserPreferences?> = _userPreferences

    fun getUserIDSync(): String? {
        return loginStatePreference.getUserIDSync()
    }

    fun loadUserPreferences() {
        viewModelScope.launch {
            loginStatePreference.getUserPreferences().collect { preferences ->
                _userPreferences.value = preferences
            }
        }
    }
}