package com.alpamedev.loginretrofit.retrofit

import com.alpamedev.loginretrofit.Constants
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserService {
    @Headers("Content-Type: application/json")
    @GET(Constants.API_PATH + Constants.USERS_PATH + Constants.TWO_PATH)
    suspend fun getUser(): UserResponse
}