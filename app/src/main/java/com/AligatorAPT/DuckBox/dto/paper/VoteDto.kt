package com.AligatorAPT.DuckBox.dto.paper

import java.io.Serializable
import java.util.*

data class VoteRegisterDto(
    val title: String,
    val content: String,
    var isGroup: Boolean,
    var groupId: String?,
    var startTime: Date,
    var finishTime: Date,
    var images: List<ByteArray>,
    var ownerPrivate: String,//private key in radix 16
    var candidates: List<String>,
    var voters: List<Int>?,
    var reward: Boolean,
    var notice: Boolean,
): Serializable

data class VoteDetailDto(
    val id: String, // ObjectId
    var title: String,
    var content: String,
    var isGroup: Boolean,
    var groupId: String?, // groupId(ObjectId) if isGroup is true
    var owner: String, // owner's nickname
    var startTime: Date,
    var finishTime: Date,
    var status: BallotStatus,
    var images: List<String>, // image list
    var candidates: List<String>,
    val voters: List<Int>?, // student id. null if isGroup is false or all group member have right to vote
    var reward: Boolean,
):Serializable

enum class BallotStatus {
    REGISTERED,
    OPEN,
    FINISHED,
}
