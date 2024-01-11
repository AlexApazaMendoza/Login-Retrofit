package com.alpamedev.loginretrofit.retrofit.service

import com.alpamedev.loginretrofit.Constants
import com.alpamedev.loginretrofit.retrofit.entities.RegisterResponse
import com.alpamedev.loginretrofit.retrofit.entities.UserRequest
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RegisterService {
    @Headers("Content-Type: application/json")
    @POST(Constants.API_PATH + Constants.REGISTER_PATH)
    suspend fun register(@Body registerRequest: UserRequest): RegisterResponse
}