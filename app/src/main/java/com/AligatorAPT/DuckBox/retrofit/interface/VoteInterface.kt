package com.AligatorAPT.DuckBox.retrofit.`interface`

import com.AligatorAPT.DuckBox.view.data.VoteDetailDto
import com.AligatorAPT.DuckBox.view.data.VoteRegisterDto
import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.http.*

interface VoteInterface {
    @POST("/api/v1/vote")
    fun register(
        @HeaderMap httpHeaders: HashMap<String, String>,
        @Body voteRegisterDto: VoteRegisterDto
    ): Call<ResponseBody>

    @GET("/api/v1/vote")
    fun getAllVote(
        @HeaderMap httpHeaders: HashMap<String, String>
    ): Call<List<VoteDetailDto>>

    @POST("/api/v1/vote/{groupId}")
    fun findVotesOfGroup(
        @HeaderMap httpHeaders: HashMap<String, String>,
        @Path ("groupId") groupId: String
    ): Call<List<VoteDetailDto>>
}