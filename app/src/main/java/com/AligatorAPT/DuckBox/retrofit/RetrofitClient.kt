package com.AligatorAPT.DuckBox.retrofit

import com.AligatorAPT.DuckBox.controller.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL:String = "http://192.168.219.103:8080"

    private val retrofit:Retrofit.Builder by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val EMAIL_CONTROLLER_SERVICE: EmailController by lazy{
        retrofit.build().create(EmailController::class.java)
    }

    val GROUP_CONTROLLER_SERVICE: GroupController by lazy{
        retrofit.build().create(GroupController::class.java)
    }

    val SMS_CONTOLLER_SERVICE: SmsContoller by lazy{
        retrofit.build().create(SmsContoller::class.java)
    }

    val USER_CONTROLLER_SERVICE: UserController by lazy{
        retrofit.build().create(UserController::class.java)
    }
}
