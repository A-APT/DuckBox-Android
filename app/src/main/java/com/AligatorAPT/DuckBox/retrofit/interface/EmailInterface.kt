package com.AligatorAPT.DuckBox.retrofit.`interface`

import com.AligatorAPT.DuckBox.dto.user.EmailTokenDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailInterface {
    @POST("/api/v1/user/email")
    fun generateEmailAuth(
        @Body targetEmail: String
    ) : Call<ResponseBody>

    @POST("/api/v1/user/email/verify")
    fun verifyEmailToken(
        @Body emailTokenDto: EmailTokenDto
    ) : Call<ResponseBody>
}
