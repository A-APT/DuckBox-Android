package com.AligatorAPT.DuckBox.retrofit

import com.AligatorAPT.DuckBox.retrofit.`interface`.*
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL:String = "http://172.30.1.58:8080"

    var gson = GsonBuilder().setLenient().create()

    private val loggingInterceptor = HttpLoggingInterceptor()


    val client: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(30000, TimeUnit.MILLISECONDS)
        .connectTimeout(30000, TimeUnit.MILLISECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit:Retrofit.Builder by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
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

    val VOTE_INTERFACE_SERVICE: VoteInterface by lazy{
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        retrofit
            .client(client)
            .build()
            .create(VoteInterface::class.java)
    }
}
