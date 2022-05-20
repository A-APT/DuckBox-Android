package com.AligatorAPT.DuckBox.model

import android.util.Log
import com.AligatorAPT.DuckBox.dto.paper.SurveyDetailDto
import com.AligatorAPT.DuckBox.dto.paper.SurveyRegisterDto
import com.AligatorAPT.DuckBox.retrofit.RetrofitClient
import com.AligatorAPT.DuckBox.retrofit.callback.RegisterCallBack
import com.AligatorAPT.DuckBox.retrofit.callback.SurveyCallback
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SurveyModel {
    fun registerSurvey(_surveyRegisterDto: SurveyRegisterDto, callback: RegisterCallBack){
        val headers = HashMap<String, String>()
        val userToken = MyApplication.prefs.getString("token", "notExist")
        Log.d("UserToken", userToken)

        headers["Authorization"] = "Bearer $userToken"

        Log.d("REGISTERDTO", _surveyRegisterDto.toString())

        RetrofitClient.SURVEY_INTERFACE_SERVICE.register(
            httpHeaders = headers,
            surveyRegisterDto = _surveyRegisterDto
        ).enqueue(object : Callback<String> {
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

    fun getAllSurvey(callback: SurveyCallback){
        val headers = HashMap<String, String>()
        val userToken = MyApplication.prefs.getString("token", "notExist")
        Log.d("UserToken", userToken)

        headers["Authorization"] = "Bearer $userToken"

        RetrofitClient.SURVEY_INTERFACE_SERVICE.getAllSurvey(httpHeaders = headers)
            .enqueue(object : Callback<List<SurveyDetailDto>> {
                override fun onResponse(call: Call<List<SurveyDetailDto>>, response: Response<List<SurveyDetailDto>>) {
                    if (response.isSuccessful) {
                        Log.d("Response:: ", response.toString())
                        callback.apiCallback(true, response.body()!!)
                    } else {
                        callback.apiCallback(false, null)
                    }
                }

                override fun onFailure(call: Call<List<SurveyDetailDto>>, t: Throwable) {
                    callback.apiCallback(false, null)
                    Log.d("onFailure::", "Failed API call with call: $call + exception: $t")
                }

            })
    }

    fun findSurveyOfGroup(groupId: String, callback: SurveyCallback){
        val headers = HashMap<String, String>()
        val userToken = MyApplication.prefs.getString("token", "notExist")
        Log.d("UserToken", userToken)

        headers["Authorization"] = "Bearer $userToken"

        RetrofitClient.SURVEY_INTERFACE_SERVICE.findSurveysOfGroup(
            httpHeaders = headers, groupId = groupId)
            .enqueue(object : Callback<List<SurveyDetailDto>> {
                override fun onResponse(
                    call: Call<List<SurveyDetailDto>>,
                    response: Response<List<SurveyDetailDto>>
                ) {
                    if (response.isSuccessful) {
                        Log.d("Response:: ", response.toString())
                        callback.apiCallback(true, response.body()!!)
                    } else {
                        callback.apiCallback(false, null)
                    }
                }

                override fun onFailure(call: Call<List<SurveyDetailDto>>, t: Throwable) {
                    callback.apiCallback(false, null)
                    Log.d("onFailure::", "Failed API call with call: $call + exception: $t")
                }

            })
    }
}