package com.AligatorAPT.DuckBox.dto.group

import retrofit2.http.Multipart

class RegisterGroupDto (
    val name: String,
    var leader: String, // did
    var description: String,
    var profile: Multipart? = null, // image
    var header: Multipart? = null, // image
)
