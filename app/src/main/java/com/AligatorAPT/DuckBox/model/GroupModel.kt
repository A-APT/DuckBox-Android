package com.AligatorAPT.DuckBox.model

import android.util.Log
import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto
import com.AligatorAPT.DuckBox.dto.group.GroupRegisterDto
import com.AligatorAPT.DuckBox.retrofit.RetrofitClient
import com.AligatorAPT.DuckBox.retrofit.callback.ApiCallback
import com.AligatorAPT.DuckBox.retrofit.callback.MyGroupCallback
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GroupModel {
    fun register(_groupRegisterDto: GroupRegisterDto, callback: ApiCallback){
        val headers = HashMap<String, String>()
        val userToken = MyApplication.prefs.getString("token", "notExist")
        Log.d("UserToken", userToken)

        headers["Authorization"] = "Bearer $userToken"

        RetrofitClient.GROUP_INTERFACE_SERVICE.register(
            httpHeaders = headers,
            groupRegisterDto = _groupRegisterDto
        ).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.d("Response:: ", response.toString())
                if (response.isSuccessful) {
                    callback.apiCallback(true)
                } else {
                    callback.apiCallback(false)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(
                    "onFailure::", "Failed API call with call: " + call +
                            " + exception: " + t
                )
            }
        })
    }

    fun getAllGroup(callback: MyGroupCallback){
        val headers = HashMap<String, String>()
        val userToken = MyApplication.prefs.getString("token", "notExist")
        Log.d("UserToken", userToken)

        headers["Authorization"] = "Bearer $userToken"

        RetrofitClient.GROUP_INTERFACE_SERVICE.getAllGroup(headers).enqueue(object :
            Callback<List<GroupDetailDto>> {
            override fun onResponse(
                call: Call<List<GroupDetailDto>>,
                response: Response<List<GroupDetailDto>>
            ) {
                if(response.isSuccessful){
                    Log.d("Response:: ", response.body().toString())
                    callback.apiCallback(true, response.body()!!)
                }else{
                    callback.apiCallback(false, null)
                }
            }

            override fun onFailure(call: Call<List<GroupDetailDto>>, t: Throwable) {
                callback.apiCallback(false, null)
                Log.d(
                    "onFailure::", "Failed API call with call: " + call +
                            " + exception: " + t
                )
            }

        })

    }
}
