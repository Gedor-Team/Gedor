package com.gedorteam.gedor.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.gedorteam.gedor.data.response.ComplaintResponseItem
import com.gedorteam.gedor.data.response.ErrorResponse
import com.gedorteam.gedor.data.retrofit.ApiService
import com.gedorteam.gedor.data.retrofit.ModelApi
import com.google.gson.Gson
import okhttp3.RequestBody
import retrofit2.HttpException

class ComplaintRepository private constructor(
    private val apiService: ApiService,
    private val modelService: ModelApi
) {

    fun getComplaints(id: Int): LiveData<Result<List<ComplaintResponseItem?>?>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getComplaints(id)
            val complaints = response.data
            emit(Result.Success(complaints))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.d("ComplaintRepository", "getComplaints: ${e.message.toString()}")
        }
    }

    fun uploadComplaint(complaint: RequestBody): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            apiService.uploadComplaint(complaint)
            emit(Result.Success("Feedback has been sent successfully"))
        } catch (e: HttpException) {
            Log.d("ComplaintRepository", "uploadComplaint: ${e.message.toString()}")
            emit(handleApiError(e))
        }
    }

    fun predictSpam(complaint: RequestBody) = liveData {
        emit(Result.Loading)
        try {
            val response = modelService.predictSpam(complaint)
            val prediction = response.predictions
            emit(Result.Success(prediction))
        } catch (e: Exception) {
            Log.d("ComplaintRepository", "predictSpam: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun predictCategory(complaint: RequestBody) = liveData {
        emit(Result.Loading)
        try {
            val response = modelService.predictCategory(complaint)
            val prediction = response.predictions
            emit(Result.Success(prediction))
        } catch (e: Exception) {
            Log.d("ComplaintRepository", "predictCategory: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    private fun handleApiError(e: HttpException): Result.Error {
        Log.d("ComplaintRepository", "API Error: ${e.message.toString()}")
        val jsonInString = e.response()?.errorBody()?.string()
        val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
        return Result.Error(errorBody.message)
    }

    companion object {
        @Volatile
        private var instance: ComplaintRepository? = null

        fun getInstance(apiService: ApiService, modelService: ModelApi): ComplaintRepository =
            instance ?: synchronized(this) {
                instance ?: ComplaintRepository(apiService, modelService)
            }.also { instance = it }
    }
}