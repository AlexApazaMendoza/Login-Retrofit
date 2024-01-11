package com.alpamedev.loginretrofit.retrofit

import com.alpamedev.loginretrofit.Constants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RegisterService {
    @Headers("Content-Type: application/json")
    @POST(Constants.API_PATH + Constants.REGISTER_PATH)
    fun register(@Body registerRequest: UserRequest): Call<RegisterResponse>
}