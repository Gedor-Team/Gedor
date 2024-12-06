package com.gedorteam.gedor.data.retrofit

import com.gedorteam.gedor.data.response.ComplaintResponse
import com.gedorteam.gedor.data.response.RegisterResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("complaints")
    suspend fun getComplaints(): ComplaintResponse

    @POST("users")
    suspend fun register(@Body requestBody: RequestBody): RegisterResponse
}