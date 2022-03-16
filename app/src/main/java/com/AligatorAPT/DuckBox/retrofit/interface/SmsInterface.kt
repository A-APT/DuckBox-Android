package com.AligatorAPT.DuckBox.retrofit.`interface`

import com.AligatorAPT.DuckBox.dto.user.SMSTokenDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SmsInterface {
    @POST("/api/v1/user/sms")
    fun generateSMSAuth(
        @Body targetNumber:String
    ): Call<ResponseBody>

    @POST("/api/v1/user/sms/token")
    fun generateSMSAuthAndReturn(
        @Body targetNumber: String
    ): Call<String>

    @POST("/api/v1/user/sms/verify")
    fun verifySMSToken(
        @Body smsTokenDto: SMSTokenDto
    ): Call<ResponseBody>

}
