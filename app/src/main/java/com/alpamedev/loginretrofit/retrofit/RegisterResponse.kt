package com.alpamedev.loginretrofit.retrofit

data class RegisterResponse(
    override val token: String,
    val id: String
): SuccessResponse()
