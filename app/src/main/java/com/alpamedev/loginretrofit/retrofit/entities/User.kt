package com.alpamedev.loginretrofit.retrofit.entities

import com.google.gson.annotations.SerializedName

data class User(
    val id: Long,
    val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val avatar: String
) {
    fun getFullName(): String = "$firstName $lastName"
}
