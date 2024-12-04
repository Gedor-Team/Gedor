package com.gedorteam.gedor.data.repositories

import android.util.Log
import androidx.lifecycle.liveData
import com.gedorteam.gedor.data.retrofit.ApiService

class UserRepository private constructor(private val apiService: ApiService) {

    fun register(username: String, email: String, password: String, phoneNumber: String) = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(username, email, password, phoneNumber)
            val result = response.data
            emit(Result.Success(result))
        } catch (e: Exception) {
            Log.d("UserRepository", "register: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object{
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also {
                instance = it
            }
    }
}