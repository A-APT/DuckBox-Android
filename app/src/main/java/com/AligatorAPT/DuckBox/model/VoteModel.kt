package com.AligatorAPT.DuckBox.model

import android.util.Log
import com.AligatorAPT.DuckBox.retrofit.callback.VoteCallback
import com.AligatorAPT.DuckBox.retrofit.RetrofitClient
import com.AligatorAPT.DuckBox.retrofit.callback.ApiCallback
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import com.AligatorAPT.DuckBox.view.data.VoteDetailDto
import com.AligatorAPT.DuckBox.view.data.VoteRegisterDto
import retrofit2.Callback
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

object VoteModel {
    fun registerVote(_voteRegisterDto: VoteRegisterDto, callback: ApiCallback){
        val headers = HashMap<String, String>()
        val userToken = MyApplication.prefs.getString("token", "notExist")
        Log.d("UserToken", userToken)

        headers["Authorization"] = "Bearer $userToken"

        Log.d("REGISTERDTO", _voteRegisterDto.toString())

        RetrofitClient.VOTE_INTERFACE_SERVICE.register(
            httpHeaders = headers,
            voteRegisterDto = _voteRegisterDto
        ).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("Response:: ", response.toString())
                if (response.isSuccessful) {
                    callback.apiCallback(true)
                } else {
                    callback.apiCallback(false)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("onFailure::", "Failed API call with call: $call + exception: $t")
            }
        })
    }

    fun getAllVote(callback: VoteCallback){
        val headers = HashMap<String, String>()
        val userToken = MyApplication.prefs.getString("token", "notExist")
        Log.d("UserToken", userToken)

        headers["Authorization"] = "Bearer $userToken"

        RetrofitClient.VOTE_INTERFACE_SERVICE.getAllVote(httpHeaders = headers)
            .enqueue(object : Callback<ArrayList<VoteDetailDto>>{
                override fun onResponse(call: Call<ArrayList<VoteDetailDto>>, response: Response<ArrayList<VoteDetailDto>>) {
                    if (response.isSuccessful) {
                        Log.d("Response:: ", response.toString())
                        callback.apiCallback(true, response.body()!!)
                    } else {
                        callback.apiCallback(false, null)
                    }
                }

                override fun onFailure(call: Call<ArrayList<VoteDetailDto>>, t: Throwable) {
                    callback.apiCallback(false, null)
                    Log.d("onFailure::", "Failed API call with call: $call + exception: $t")
                }

            })
    }

    fun findVotesOfGroup(groupId: String, callback: VoteCallback){
        val headers = HashMap<String, String>()
        val userToken = MyApplication.prefs.getString("token", "notExist")
        Log.d("UserToken", userToken)

        headers["Authorization"] = "Bearer $userToken"

        RetrofitClient.VOTE_INTERFACE_SERVICE.findVotesOfGroup(
            httpHeaders = headers, groupId = groupId)
            .enqueue(object : Callback<ArrayList<VoteDetailDto>>{
                override fun onResponse(
                    call: Call<ArrayList<VoteDetailDto>>,
                    response: Response<ArrayList<VoteDetailDto>>
                ) {
                    if (response.isSuccessful) {
                        Log.d("Response:: ", response.toString())
                        callback.apiCallback(true, response.body()!!)
                    } else {
                        callback.apiCallback(false, null)
                    }
                }

                override fun onFailure(call: Call<ArrayList<VoteDetailDto>>, t: Throwable) {
                    callback.apiCallback(false, null)
                    Log.d("onFailure::", "Failed API call with call: $call + exception: $t")
                }

            })
    }
}