package com.gedorteam.gedor.di.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gedorteam.gedor.data.local.preferences.LoginStatePreference
import com.gedorteam.gedor.di.Injection
import com.gedorteam.gedor.ui.settings.SettingsViewModel

class SettingsViewModelFactory private constructor(
    private val loginStatePreference: LoginStatePreference
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when (modelClass) {
            SettingsViewModel::class.java -> {
                return SettingsViewModel(loginStatePreference) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: SettingsViewModelFactory? = null
        fun getInstance(context: Context): SettingsViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: SettingsViewModelFactory(
                    Injection.provideLoginStatePreference(context)
                )
            }.also {
                instance = it
            }
    }
}