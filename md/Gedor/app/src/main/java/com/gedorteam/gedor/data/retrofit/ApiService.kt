package com.gedorteam.gedor.data.retrofit

import com.gedorteam.gedor.data.response.ComplaintResponse
import com.gedorteam.gedor.data.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("complaints")
    suspend fun getComplaints(): ComplaintResponse

    @FormUrlEncoded
    @POST("users")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("phoneNumber") phoneNumber: String
    ): RegisterResponse

//    @POST("users")
//    suspend fun register(username: String, email: String, password: String, phoneNumber: String): RegisterResponse
}