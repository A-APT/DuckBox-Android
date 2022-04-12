package com.AligatorAPT.DuckBox.dto.group

import java.io.Serializable

enum class GroupStatus {
    PENDING, // [인증전]
    VALID, // [활성화]
    DELETED, // [삭제된]
    REPORTED, // [신고된]
}

data class GroupDetailDto (
    val id: String, // ObjectId
    val name: String,
    var leader: String, // did
    var status: GroupStatus,
    var description: String,
    var profile: ByteArray? = null, // image
    var header: ByteArray? = null, // image
): Serializable {
}
