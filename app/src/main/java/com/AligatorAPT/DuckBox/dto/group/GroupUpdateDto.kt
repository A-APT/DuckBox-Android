package com.AligatorAPT.DuckBox.dto.group

data class GroupUpdateDto (
    var id: String, // ObjectId
    var description: String?,
    var profile: ByteArray?, // image
    var header: ByteArray?, // image
)
