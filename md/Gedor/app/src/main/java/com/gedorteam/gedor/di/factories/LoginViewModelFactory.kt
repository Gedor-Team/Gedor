package com.gedorteam.gedor.di.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gedorteam.gedor.data.local.preferences.LoginStatePreference
import com.gedorteam.gedor.data.repositories.UserRepository
import com.gedorteam.gedor.di.Injection
import com.gedorteam.gedor.ui.login.LoginViewModel

class LoginViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val loginStatePreference: LoginStatePreference
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userRepository, loginStatePreference) as T
        }

        throw IllegalArgumentException("Unknown viewModel clas: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: LoginViewModelFactory? = null
        fun getInstance(context: Context
        ): LoginViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: LoginViewModelFactory(
                    Injection.provideUserRepository(),
                    Injection.provideLoginStatePreference(context))
            }.also {
                instance = it
            }
    }
}