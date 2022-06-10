package com.AligatorAPT.DuckBox.retrofit.`interface`

import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto
import com.AligatorAPT.DuckBox.dto.group.GroupRegisterDto
import com.AligatorAPT.DuckBox.dto.group.GroupUpdateDto
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

    @POST("/api/v1/group/detail")
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

    @DELETE("/api/v1/group")
    fun removeGroup(
        @HeaderMap httpHeaders: HashMap<String, String>,
        @Body groupId: String
    ): Call<ResponseBody>

    @DELETE("/api/v1/group/member")
    fun leaveGroup(
        @HeaderMap httpHeaders: HashMap<String, String>,
        @Body groupId: String
    ): Call<ResponseBody>
}
