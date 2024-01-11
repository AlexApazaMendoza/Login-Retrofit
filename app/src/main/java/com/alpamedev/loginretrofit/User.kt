package com.alpamedev.loginretrofit

data class User(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
) {
    fun getFullName(): String = "$firstName $lastName"
}
