package com.AligatorAPT.DuckBox.model

import android.util.Log
import com.AligatorAPT.DuckBox.dto.user.LoginRequestDto
import com.AligatorAPT.DuckBox.dto.user.LoginResponseDto
import com.AligatorAPT.DuckBox.dto.user.RegisterDto
import com.AligatorAPT.DuckBox.ethereum.DIDContract
import com.AligatorAPT.DuckBox.ethereum.GanacheAddress
import com.AligatorAPT.DuckBox.retrofit.RetrofitClient
import com.AligatorAPT.DuckBox.retrofit.callback.ApiCallback
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
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
                    MyApplication.prefs.setString("studentId", response.body()!!.studentId.toString())
                    MyApplication.prefs.setString("did", response.body()!!.did)
                    MyApplication.prefs.setString("nickname", response.body()!!.nickname)
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

    fun joinGroup(_groupId:String, callback: ApiCallback){
        val headers = HashMap<String, String>()
        val userToken = MyApplication.prefs.getString("token", "notExist")
        Log.d("UserToken", userToken)

        headers["Authorization"] = "Bearer $userToken"

        //String to text/plain
        val groupIdTextPlain: RequestBody = RequestBody.create(MediaType.parse("text/plain"), _groupId)

        RetrofitClient.USER_INTERFACE_SERVICE.joinGroup(
            groupId = groupIdTextPlain,
            httpHeaders = headers
        ).enqueue(object :Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
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
