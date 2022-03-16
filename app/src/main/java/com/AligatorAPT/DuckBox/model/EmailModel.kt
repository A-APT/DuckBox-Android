package com.AligatorAPT.DuckBox.model

import android.util.Log
import com.AligatorAPT.DuckBox.dto.user.EmailTokenDto
import com.AligatorAPT.DuckBox.retrofit.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailModel {
    fun generateEmailAuth(_email: String){
        RetrofitClient.EMAIL_INTERFACE_SERVICE.generateEmailAuth(_email).enqueue(object:
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.d("Response:: ", response.body().toString())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("onFailure::", "Failed API call with call: " + call +
                        " + exception: " + t)
            }
        })
    }

    fun verifyEmailToken(_emailTokenDto: EmailTokenDto): Boolean{
        var isVerified = false

        RetrofitClient.EMAIL_INTERFACE_SERVICE.verifyEmailToken(_emailTokenDto).enqueue(object:
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.d("Response:: ", response.body().toString())
                isVerified = true
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("onFailure::", "Failed API call with call: " + call +
                        " + exception: " + t)
                isVerified = false
            }
        })

        return isVerified
    }
}
