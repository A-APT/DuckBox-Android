package com.AligatorAPT.DuckBox.retrofit.`interface`

import com.AligatorAPT.DuckBox.dto.user.LoginRequestDto
import com.AligatorAPT.DuckBox.dto.user.LoginResponseDto
import com.AligatorAPT.DuckBox.dto.user.RegisterDto
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface UserInterface {
    @POST("/api/v1/user/login")
    fun login(
        @Body loginRequestDto: LoginRequestDto
    ): Call<LoginResponseDto>

    @POST("/api/v1/user/refresh")
    fun refreshToken(
        @Body refreshToken: String
    ): Call<LoginResponseDto>

    @POST("/api/v1/user/register")
    fun register(
        @Body registerDto: RegisterDto
    ): Call<String>

    @POST("/api/v1/user/group")
    fun joinGroup(
        @Body groupId: RequestBody,
        @HeaderMap httpHeaders: HashMap<String, String>
    ): Call<ResponseBody>
}
