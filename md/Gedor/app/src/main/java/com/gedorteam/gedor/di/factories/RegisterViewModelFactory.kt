package com.gedorteam.gedor.di.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gedorteam.gedor.data.repositories.UserRepository
import com.gedorteam.gedor.di.Injection
import com.gedorteam.gedor.ui.register.RegisterViewModel

class RegisterViewModelFactory private constructor(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: RegisterViewModelFactory? = null
        fun getInstance(): RegisterViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: RegisterViewModelFactory(Injection.provideUserRepository())
            }.also { instance = it }
    }
}