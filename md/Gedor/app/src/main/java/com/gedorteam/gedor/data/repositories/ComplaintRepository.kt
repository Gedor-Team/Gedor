package com.gedorteam.gedor.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.gedorteam.gedor.data.response.ComplaintResponseItem
import com.gedorteam.gedor.data.response.ErrorResponse
import com.gedorteam.gedor.data.retrofit.ApiService
import com.google.gson.Gson
import okhttp3.RequestBody
import retrofit2.HttpException

class ComplaintRepository private constructor(private val apiService: ApiService) {

    fun getComplaints(): LiveData<Result<List<ComplaintResponseItem?>?>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getComplaints()
            val complaints = response.complaintResponse
            emit(Result.Success(complaints))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.d("ComplaintRepository", "getComplaints: ${e.message.toString()}")
        }
    }

    fun uploadComplaint(complaint: RequestBody): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.uploadComplaint(complaint)
//            val message = response.message
            emit(Result.Success("Your feedback has been sent"))
        } catch (e: HttpException) {
            Log.d("ComplaintRepository", "uploadComplaint: ${e.message.toString()}")
            emit(handleApiError(e))
//            val jsonInString = e.response()?.errorBody()?.string()
//            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
//            val errorMessage = errorBody.error
//            emit(Result.Error(errorMessage))
        }
    }

    fun predictSpam(complaint: RequestBody) = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.predictSpam(complaint)
            val prediction = response.predictions
            emit(Result.Success(prediction))
        } catch (e: Exception) {
            Log.d("ComplaintRepository", "predictSpam: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
//            handleApiError(e)
//            val jsonInString = e.response()?.errorBody()?.string()
//            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
//            val errorMessage = errorBody.error
//            emit(Result.Error(errorMessage))
        }
    }

    fun predictCategory(complaint: RequestBody) = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.predictCategory(complaint)
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
        return Result.Error(errorBody.error)
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