package com.gedorteam.gedor.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gedorteam.gedor.data.local.preferences.LoginStatePreference
import com.gedorteam.gedor.data.repositories.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository, private val loginStatePreference: LoginStatePreference) : ViewModel() {
    fun login(username: String) =
        userRepository.login(username)

    fun saveLoginState(userID: String, username: String, email: String, phoneNumber: String) {
        viewModelScope.launch {
            loginStatePreference.saveLoginState(userID, username, email, phoneNumber)
        }
    }
}