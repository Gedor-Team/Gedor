package com.gedorteam.gedor.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.gedorteam.gedor.data.response.DataItem
import com.gedorteam.gedor.data.retrofit.ApiService

class ComplaintRepository private constructor(private val apiService: ApiService) {

    fun getComplaints(): LiveData<Result<List<DataItem?>?>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getComplaints()
            val complaints = response.data
            emit(Result.Success(complaints))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.d("ComplaintRepository", "getComplaints: ${e.message.toString()}")
        }
    }

    companion object {
        @Volatile
        private var instance: ComplaintRepository? = null

        fun getInstance(apiService: ApiService): ComplaintRepository =
            instance ?: synchronized(this) {
                instance ?: ComplaintRepository(apiService)
            }.also { instance = it }
    }
}