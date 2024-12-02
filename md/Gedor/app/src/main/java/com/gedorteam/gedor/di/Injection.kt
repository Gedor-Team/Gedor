package com.gedorteam.gedor.di

import com.gedorteam.gedor.data.repositories.ComplaintRepository
import com.gedorteam.gedor.data.retrofit.ApiConfig

object Injection {
    fun provideComplaintRepository(): ComplaintRepository {
        val apiService = ApiConfig.getApiService()
        return ComplaintRepository.getInstance(apiService)
    }
}