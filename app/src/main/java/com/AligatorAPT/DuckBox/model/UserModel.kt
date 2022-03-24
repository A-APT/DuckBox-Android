package com.AligatorAPT.DuckBox.model

import android.util.Log
import com.AligatorAPT.DuckBox.dto.user.RegisterDto
import com.AligatorAPT.DuckBox.retrofit.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserModel{
    fun register( _userInfo: RegisterDto){
        Log.d("USER", _userInfo.toString())
        RetrofitClient.USER_INTERFACE_SERVICE.register(
            _userInfo
        ).enqueue(object:
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
}
