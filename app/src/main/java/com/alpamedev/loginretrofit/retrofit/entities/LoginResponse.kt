package com.alpamedev.loginretrofit.retrofit.entities

data class LoginResponse(
    override val token: String
): SuccessResponse()
