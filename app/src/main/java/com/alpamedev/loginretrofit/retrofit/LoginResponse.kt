package com.alpamedev.loginretrofit.retrofit

data class LoginResponse(
    override val token: String
): SuccessResponse()
