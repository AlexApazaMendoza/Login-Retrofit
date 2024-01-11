package com.alpamedev.loginretrofit.retrofit

import com.alpamedev.loginretrofit.Constants
import com.google.gson.annotations.SerializedName

data class UserRequest (
    @SerializedName(Constants.EMAIL_PARAM) val email: String,
    @SerializedName(Constants.PASSWORD_PARAM) val password: String
)
