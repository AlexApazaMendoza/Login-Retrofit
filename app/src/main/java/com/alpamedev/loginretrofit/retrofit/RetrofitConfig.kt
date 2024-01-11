package com.alpamedev.loginretrofit.retrofit

import com.alpamedev.loginretrofit.Constants
import com.alpamedev.loginretrofit.retrofit.service.LoginService
import com.alpamedev.loginretrofit.retrofit.service.RegisterService
import com.alpamedev.loginretrofit.retrofit.service.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var loginService: LoginService = retrofit.create(LoginService::class.java)
    val registerService: RegisterService = retrofit.create(RegisterService::class.java)
    val userService: UserService = retrofit.create(UserService::class.java)
}