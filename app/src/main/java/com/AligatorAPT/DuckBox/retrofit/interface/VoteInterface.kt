package com.AligatorAPT.DuckBox.retrofit.`interface`

import com.AligatorAPT.DuckBox.dto.paper.VoteDetailDto
import com.AligatorAPT.DuckBox.dto.paper.VoteRegisterDto
import com.AligatorAPT.DuckBox.dto.user.BlindSigRequestDto
import com.AligatorAPT.DuckBox.dto.user.BlindSigToken
import retrofit2.Call
import retrofit2.http.*

interface VoteInterface {
    @POST("/api/v1/vote")
    fun register(
        @HeaderMap httpHeaders: HashMap<String, String>,
        @Body voteRegisterDto: VoteRegisterDto
    ): Call<String>

    @GET("/api/v1/vote")
    fun getAllVote(
        @HeaderMap httpHeaders: HashMap<String, String>
    ): Call<ArrayList<VoteDetailDto>>

    @GET("/api/v1/vote/{voteId}")
    fun findVoteById(
        @HeaderMap httpHeaders: HashMap<String, String>,
        @Path ("voteId") voteId: String
    ): Call<VoteDetailDto>

    @GET("/api/v1/vote/group/{groupId}")
    fun findVotesOfGroup(
        @HeaderMap httpHeaders: HashMap<String, String>,
        @Path ("groupId") groupId: String
    ): Call<ArrayList<VoteDetailDto>>

    @POST("/api/v1/vote/signatures")
    fun generateVoteToken(
        @HeaderMap httpHeaders: HashMap<String, String>,
        @Body blindSigRequestDto: BlindSigRequestDto
    ): Call<BlindSigToken>
}