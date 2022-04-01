package com.AligatorAPT.DuckBox.model

import android.util.Log
import com.AligatorAPT.DuckBox.dto.user.LoginRequestDto
import com.AligatorAPT.DuckBox.dto.user.LoginResponseDto
import com.AligatorAPT.DuckBox.dto.user.RegisterDto
import com.AligatorAPT.DuckBox.retrofit.RetrofitClient
import com.AligatorAPT.DuckBox.retrofit.callback.ApiCallback
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserModel{
    fun register( _userInfo: RegisterDto, callback: ApiCallback){
        Log.d("USER", _userInfo.toString())
        RetrofitClient.USER_INTERFACE_SERVICE.register(
            _userInfo
        ).enqueue(object:
            Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    MyApplication.prefs.setString("did", response.body()!!)
                    Log.d("DID", response.body()!!)
                    callback.apiCallback(true)
                }else{
                    callback.apiCallback(false)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("onFailure::", "Failed API call with call: " + call +
                        " + exception: " + t)
            }
        })
    }

    fun login(_loginRequestDto: LoginRequestDto, callback: ApiCallback){
        RetrofitClient.USER_INTERFACE_SERVICE.login(
            _loginRequestDto
        ).enqueue(object :Callback<LoginResponseDto>{
            override fun onResponse(
                call: Call<LoginResponseDto>,
                response: Response<LoginResponseDto>
            ) {
                Log.d("Response:: ", response.toString())
                if(response.isSuccessful){
                    MyApplication.prefs.setString("token", response.body()!!.token)
                    MyApplication.prefs.setString("refreshToken", response.body()!!.refreshToken)
                    callback.apiCallback(true)
                }else{
                    callback.apiCallback(false)
                }
            }

            override fun onFailure(call: Call<LoginResponseDto>, t: Throwable) {
                Log.d("onFailure::", "Failed API call with call: " + call +
                        " + exception: " + t)
            }
        })
    }
}
