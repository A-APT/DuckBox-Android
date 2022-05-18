package com.AligatorAPT.DuckBox.retrofit.`interface`

import com.AligatorAPT.DuckBox.dto.user.BlindSigRequestDto
import com.AligatorAPT.DuckBox.dto.user.BlindSigToken
import com.AligatorAPT.DuckBox.dto.vote.SurveyDetailDto
import com.AligatorAPT.DuckBox.dto.vote.SurveyRegisterDto
import retrofit2.Call
import retrofit2.http.*

interface SurveyInterface {
    @POST("/api/v1/survey")
    fun register(
        @HeaderMap httpHeaders: HashMap<String, String>,
        @Body surveyRegisterDto: SurveyRegisterDto
    ): Call<String>

    @GET("/api/v1/survey")
    fun getAllSurvey(
        @HeaderMap httpHeaders: HashMap<String, String>
    ): Call<List<SurveyDetailDto>>

    @GET("/api/v1/survey/group/{groupId}")
    fun findSurveysOfGroup(
        @HeaderMap httpHeaders: HashMap<String, String>,
        @Path("groupId") groupId: String
    ): Call<List<SurveyDetailDto>>

    @POST("/api/v1/survey/signatures")
    fun generateSurveyToken(
        @HeaderMap httpHeaders: HashMap<String, String>,
        @Body blindSigRequestDto: BlindSigRequestDto
    ): Call<BlindSigToken>
}