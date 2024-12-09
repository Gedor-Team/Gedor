package com.gedorteam.gedor.data.retrofit

import com.gedorteam.gedor.data.response.PredictCategoryModelResponse
import com.gedorteam.gedor.data.response.SpamCheckModelResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ModelApi {
    @POST("models/complaint")
    suspend fun predictSpam(@Body complaint: RequestBody): SpamCheckModelResponse

    @POST("models/category")
    suspend fun predictCategory(@Body complaint: RequestBody): PredictCategoryModelResponse
}