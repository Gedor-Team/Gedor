package com.gedorteam.gedor.data.repositories

import android.util.Log
import androidx.lifecycle.liveData
import com.gedorteam.gedor.data.response.RegisterResponse
import com.gedorteam.gedor.data.retrofit.ApiService
import com.google.gson.Gson
import okhttp3.RequestBody
import retrofit2.HttpException

class UserRepository private constructor(private val apiService: ApiService) {

    fun register(requestBody: RequestBody) = liveData {

        emit(Result.Loading)
        try {
            val response = apiService.register(requestBody)
            val result = response.data
            emit(Result.Success(result))
        } catch (e: HttpException) {
            Log.d("UserRepository", "register: ${e.message.toString()}")
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage ?: "Something wrong"))
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