package com.alpamedev.loginretrofit.retrofit.entities

data class RegisterResponse(
    override val token: String,
    val id: String
): SuccessResponse()
