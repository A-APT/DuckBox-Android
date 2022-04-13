package com.AligatorAPT.DuckBox.model

import android.util.Log
import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto
import com.AligatorAPT.DuckBox.dto.group.GroupRegisterDto
import com.AligatorAPT.DuckBox.dto.group.GroupUpdateDto
import com.AligatorAPT.DuckBox.retrofit.RetrofitClient
import com.AligatorAPT.DuckBox.retrofit.callback.ApiCallback
import com.AligatorAPT.DuckBox.retrofit.callback.MyGroupCallback
import com.AligatorAPT.DuckBox.retrofit.callback.RegisterCallBack
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GroupModel {
    fun register(_groupRegisterDto: GroupRegisterDto, callback: RegisterCallBack){
        val headers = HashMap<String, String>()
        val userToken = MyApplication.prefs.getString("token", "notExist")
        Log.d("UserToken", userToken)

        headers["Authorization"] = "Bearer $userToken"

        Log.d("REGISTERDTO", _groupRegisterDto.toString())

        RetrofitClient.GROUP_INTERFACE_SERVICE.register(
            httpHeaders = headers,
            groupRegisterDto = _groupRegisterDto
        ).enqueue(object :
            Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                Log.d("Response:: ", response.toString())
                if (response.isSuccessful) {
                    callback.registerCallBack(true, response.body()!!)
                } else {
                    callback.registerCallBack(false, null)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(
                    "onFailure::", "Failed RegisterGroup API call with call: " + call +
                            " + exception: " + t
                )
                callback.registerCallBack(false, null)
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
                    "onFailure::", "Failed GetAllGroup API call with call: " + call +
                            " + exception: " + t
                )
            }

        })
    }

    fun updateGroup(_groupUpdateDto: GroupUpdateDto, callback: ApiCallback){
        val headers = HashMap<String, String>()
        val userToken = MyApplication.prefs.getString("token", "notExist")
        Log.d("UserToken", userToken)
        headers["Authorization"] = "Bearer $userToken"

        RetrofitClient.GROUP_INTERFACE_SERVICE.updateGroup(
            groupUpdateDto = _groupUpdateDto,
            httpHeaders = headers
        ).enqueue(object: Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    Log.d("Response:: ", response.body().toString())
                    callback.apiCallback(true)
                }else{
                    callback.apiCallback(false)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.apiCallback(false)
                Log.d(
                    "onFailure::", "Failed UpdateGroup call with call: " + call +
                            " + exception: " + t
                )
            }
        })
    }

}
