package com.AligatorAPT.DuckBox.retrofit.`interface`

import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto
import com.AligatorAPT.DuckBox.dto.group.GroupRegisterDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface GroupInterface {
    @POST("/api/v1/group")
    fun register(
        @HeaderMap httpHeaders: HashMap<String, String>,
        @Body groupRegisterDto: GroupRegisterDto
        ): Call<ResponseBody>

    @GET("/api/v1/group")
    fun getAllGroup(
        @HeaderMap httpHeaders: HashMap<String, String>
    ): Call<List<GroupDetailDto>>
}
