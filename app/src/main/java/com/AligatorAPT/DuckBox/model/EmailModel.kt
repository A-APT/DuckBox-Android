package com.AligatorAPT.DuckBox.model

import android.util.Log
import com.AligatorAPT.DuckBox.dto.user.EmailTokenDto
import com.AligatorAPT.DuckBox.retrofit.RetrofitClient
import com.AligatorAPT.DuckBox.retrofit.`interface`.ApiCallback
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object EmailModel {
    fun generateEmailAuth(_email: String, callback: ApiCallback){
        RetrofitClient.EMAIL_INTERFACE_SERVICE.generateEmailAuth(_email).enqueue(object:
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.d("Response:: ", response.toString())
                if(response.isSuccessful){
                    callback.apiCallback(true)
                }else{
                    callback.apiCallback(false)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("onFailure::", "Failed API call with call: " + call +
                        " + exception: " + t)
            }
        })
    }

    fun verifyEmailToken(_emailTokenDto: EmailTokenDto, callback: ApiCallback){
        RetrofitClient.EMAIL_INTERFACE_SERVICE.verifyEmailToken(_emailTokenDto).enqueue(object:
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.d("Response:: ", response.toString())
                if(response.isSuccessful){
                    callback.apiCallback(true)
                }else{
                    callback.apiCallback(false)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("onFailure::", "Failed API call with call: " + call +
                        " + exception: " + t)
                callback.apiCallback(false)
            }
        })
    }
}
