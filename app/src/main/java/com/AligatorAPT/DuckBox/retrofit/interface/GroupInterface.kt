package com.AligatorAPT.DuckBox.retrofit.`interface`

import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto
import com.AligatorAPT.DuckBox.dto.group.GroupRegisterDto
import com.AligatorAPT.DuckBox.dto.group.GroupUpdateDto
import com.AligatorAPT.DuckBox.dto.group.ReportRequestDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface GroupInterface {
    @POST("/api/v1/group")
    fun register(
        @HeaderMap httpHeaders: HashMap<String, String>,
        @Body groupRegisterDto: GroupRegisterDto
        ): Call<String>

    @GET("/api/v1/groups/all")
    fun getAllGroup(
        @HeaderMap httpHeaders: HashMap<String, String>
    ): Call<List<GroupDetailDto>>

    @PUT("/api/v1/group")
    fun updateGroup(
        @Body groupUpdateDto: GroupUpdateDto,
        @HeaderMap httpHeaders: HashMap<String, String>
    ): Call<ResponseBody>

    @GET("/api/v1/groups")
    fun getGroupsOfUser(
        @HeaderMap httpHeaders: HashMap<String, String>
    ): Call<List<GroupDetailDto>>

    @GET("/api/v1/groups/{query}")
    fun searchGroup(
        @HeaderMap httpHeaders: HashMap<String, String>,
        @Path("query") query: String
    ): Call<ArrayList<GroupDetailDto>>

    @GET("/api/v1/group/{groupId}")
    fun findGroupById(
        @HeaderMap httpHeaders: HashMap<String, String>,
        @Path("groupId") groupId: String
    ): Call<GroupDetailDto>

    @POST("/api/v1/group/status")
    fun reportGroup(
        @HeaderMap httpHeaders: HashMap<String, String>,
        @Body reportRequestDto: ReportRequestDto
    ): Call<ResponseBody>
}
