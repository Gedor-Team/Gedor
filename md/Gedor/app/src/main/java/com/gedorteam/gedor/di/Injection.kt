package com.gedorteam.gedor.di

import android.content.Context
import com.gedorteam.gedor.data.local.preferences.LoginStatePreference
import com.gedorteam.gedor.data.local.preferences.dataStore
import com.gedorteam.gedor.data.repositories.ComplaintRepository
import com.gedorteam.gedor.data.repositories.UserRepository
import com.gedorteam.gedor.data.retrofit.ApiConfig

object Injection {
    fun provideComplaintRepository(): ComplaintRepository {
        val apiService = ApiConfig.getApiService()
        val modelService = ApiConfig.getModelApiService()
        return ComplaintRepository.getInstance(apiService, modelService)
    }

    fun provideUserRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }

    fun provideLoginStatePreference(context: Context): LoginStatePreference {
        return LoginStatePreference.getInstance(context.dataStore)
    }
}