package com.alpamedev.loginretrofit.retrofit.service

import com.alpamedev.loginretrofit.Constants
import com.alpamedev.loginretrofit.retrofit.entities.UserResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserService {
    @Headers("Content-Type: application/json")
    @GET(Constants.API_PATH + Constants.USERS_PATH + Constants.TWO_PATH)
    suspend fun getUser(): UserResponse
}