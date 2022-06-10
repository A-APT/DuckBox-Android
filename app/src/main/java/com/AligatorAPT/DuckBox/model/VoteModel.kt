package com.AligatorAPT.DuckBox.model

import android.util.Log
import com.AligatorAPT.DuckBox.retrofit.callback.VoteCallback
import com.AligatorAPT.DuckBox.retrofit.RetrofitClient
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import com.AligatorAPT.DuckBox.dto.paper.VoteDetailDto
import com.AligatorAPT.DuckBox.dto.paper.VoteRegisterDto
import com.AligatorAPT.DuckBox.dto.user.BlindSigRequestDto
import com.AligatorAPT.DuckBox.dto.user.BlindSigToken
import com.AligatorAPT.DuckBox.retrofit.callback.RegisterCallBack
import com.AligatorAPT.DuckBox.retrofit.callback.TokenCallback
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

object VoteModel {
    fun registerVote(_voteRegisterDto: VoteRegisterDto, callback: RegisterCallBack){
        val headers = HashMap<String, String>()
        val userToken = MyApplication.prefs.getString("token", "notExist")
        Log.d("UserToken", userToken)

        headers["Authorization"] = "Bearer $userToken"

        Log.d("REGISTERDTO", _voteRegisterDto.toString())

        RetrofitClient.VOTE_INTERFACE_SERVICE.register(
            httpHeaders = headers,
            voteRegisterDto = _voteRegisterDto
        ).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("Response:: ", response.toString())
                if (response.isSuccessful) {
                    callback.registerCallBack(true, response.body())
                } else {
                    callback.registerCallBack(false, response.body())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
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
                        callback.apiCallback(true, response.body())
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

    fun generateVoteToken(blindSigRequestDto: BlindSigRequestDto, callback: TokenCallback){
        val headers = HashMap<String, String>()
        val userToken = MyApplication.prefs.getString("token", "notExist")
        Log.d("UserToken", userToken)

        headers["Authorization"] = "Bearer $userToken"

        RetrofitClient.VOTE_INTERFACE_SERVICE.generateVoteToken(
            httpHeaders = headers, blindSigRequestDto = blindSigRequestDto)
            .enqueue(object : Callback<BlindSigToken>{
                override fun onResponse(
                    call: Call<BlindSigToken>,
                    response: Response<BlindSigToken>
                ) {
                    if (response.isSuccessful) {
                        Log.d("Response:: ", response.toString())
                        callback.tokenCallback(true, response.body()!!)
                    } else {
                        callback.tokenCallback(false, null)
                    }
                }

                override fun onFailure(call: Call<BlindSigToken>, t: Throwable) {
                    callback.tokenCallback(false, null)
                    Log.d("onFailure::", "Failed API call with call: $call + exception: $t")
                }

            })
    }
}