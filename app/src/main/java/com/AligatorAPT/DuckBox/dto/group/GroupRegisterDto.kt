package com.AligatorAPT.DuckBox.dto.group

data class GroupRegisterDto (
    val name: String,
    var leader: String, // did
    var description: String,
    var profile: ByteArray?, // image
    var header: ByteArray?, // image
)
