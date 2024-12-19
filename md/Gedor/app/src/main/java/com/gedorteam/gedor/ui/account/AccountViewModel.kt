package com.gedorteam.gedor.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gedorteam.gedor.data.local.preferences.LoginStatePreference
import com.gedorteam.gedor.data.local.preferences.UserPreferences
import com.gedorteam.gedor.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class AccountViewModel(
    private val userRepository: UserRepository,
    private val loginStatePreference: LoginStatePreference
) : ViewModel() {

    private val _userPreferences = MutableStateFlow<UserPreferences?>(null)
    val userPreferences: StateFlow<UserPreferences?> = _userPreferences

    fun loadUserPreferences() {
        viewModelScope.launch {
            loginStatePreference.getUserPreferences().collect { preferences ->
                _userPreferences.value = preferences
            }
        }
    }

    fun updateUserInfo(userID: String, requestBody: RequestBody) = userRepository.updateUserInfo(userID, requestBody)

    fun saveUserInfo(userID: String, username: String, email: String, phoneNumber: String) {
        viewModelScope.launch {
            loginStatePreference.saveLoginState(userID, username, email, phoneNumber)
        }
    }
}