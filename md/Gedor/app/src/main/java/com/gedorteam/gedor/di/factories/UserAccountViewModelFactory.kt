package com.gedorteam.gedor.di.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gedorteam.gedor.data.repositories.UserRepository
import com.gedorteam.gedor.di.Injection
import com.gedorteam.gedor.ui.register.RegisterViewModel

class UserAccountViewModelFactory private constructor(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(userRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: UserAccountViewModelFactory? = null
        fun getInstance(): UserAccountViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: UserAccountViewModelFactory(Injection.provideUserRepository())
            }.also { instance = it }
    }
}