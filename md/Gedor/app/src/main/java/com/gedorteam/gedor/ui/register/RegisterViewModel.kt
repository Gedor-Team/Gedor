package com.gedorteam.gedor.ui.register

import androidx.lifecycle.ViewModel
import com.gedorteam.gedor.data.repositories.UserRepository
import okhttp3.RequestBody

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun register(requestBody: RequestBody) =
        userRepository.register(requestBody)
}