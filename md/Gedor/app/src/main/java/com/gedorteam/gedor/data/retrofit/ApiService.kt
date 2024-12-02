package com.gedorteam.gedor.data.retrofit

import com.gedorteam.gedor.data.response.ComplaintResponse
import retrofit2.http.GET

interface ApiService {
    @GET("complaints")
    suspend fun getComplaints(): ComplaintResponse
}