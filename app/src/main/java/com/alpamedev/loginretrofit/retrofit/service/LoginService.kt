package com.alpamedev.loginretrofit.retrofit.service

import com.alpamedev.loginretrofit.Constants
import com.alpamedev.loginretrofit.retrofit.entities.LoginResponse
import com.alpamedev.loginretrofit.retrofit.entities.UserRequest
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @Headers("Content-Type: application/json")
    @POST(Constants.API_PATH + Constants.LOGIN_PATH)
    suspend fun login(@Body loginRequest: UserRequest): LoginResponse
}