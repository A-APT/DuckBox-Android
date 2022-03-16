package com.AligatorAPT.DuckBox.controller

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.File
import java.util.*

interface GroupController {
    @POST("/api/v1/group/register")
    fun register(
        @Query("description") description: String,
        @Query("header") header: File,
        @HeaderMap httpHeaders: HashMap<String, Any?>,
        @Query("leader") leader: String,
        @Query("name") name: String,
        @Query("profile") profile: File,
        ): Call<ResponseBody>
}
