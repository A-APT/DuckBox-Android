package com.AligatorAPT.DuckBox.retrofit

import com.AligatorAPT.DuckBox.retrofit.`interface`.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL:String = "http://192.168.219.103:8080"

    private val retrofit:Retrofit.Builder by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val EMAIL_INTERFACE_SERVICE: EmailInterface by lazy{
        retrofit.build().create(EmailInterface::class.java)
    }

    val GROUP_INTERFACE_SERVICE: GroupInterface by lazy{
        retrofit.build().create(GroupInterface::class.java)
    }

    val SMS_INTERFACE_SERVICE: SmsInterface by lazy{
        retrofit.build().create(SmsInterface::class.java)
    }

    val USER_INTERFACE_SERVICE: UserInterface by lazy{
        retrofit.build().create(UserInterface::class.java)
    }
}
