package com.AligatorAPT.DuckBox.retrofit.`interface`

import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto
import com.AligatorAPT.DuckBox.dto.group.GroupRegisterDto
import com.AligatorAPT.DuckBox.dto.group.GroupUpdateDto
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
        ): Call<String>

    @GET("/api/v1/group/all")
    fun getAllGroup(
        @HeaderMap httpHeaders: HashMap<String, String>
    ): Call<List<GroupDetailDto>>

    @POST("/api/v1/group/detail")
    fun updateGroup(
        @Body groupUpdateDto: GroupUpdateDto,
        @HeaderMap httpHeaders: HashMap<String, String>
    ): Call<ResponseBody>

    @GET("/api/v1/group/my")
    fun getGroupsOfUser(
        @HeaderMap httpHeaders: HashMap<String, String>
    ): Call<List<GroupDetailDto>>
}
