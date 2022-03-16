package com.AligatorAPT.DuckBox.dto.user

data class LoginResponseDto (
    val token: String,
    val refreshToken: String
)
