package com.AligatorAPT.DuckBox.dto.vote

import java.util.*

data class VoteRegisterDto(
    val title: String,
    val content: String,
    var isGroup: Boolean,
    var groupId: String?, // groupId(ObjectId) if isGroup is true
    val startTime: Date,
    val finishTime: Date,
    var images: List<ByteArray>, // image list
    var ownerPrivate: String, // private key in radix 16
    val candidates: List<String>,
    val voters: List<Int>?, // student id. null if isGroup is false or all group member have right to vote
    val reward: Boolean,
    var notice: Boolean
)

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
    var images: List<ByteArray>, // image list
    var candidates: List<String>,
    val voters: List<Int>?, // student id. null if isGroup is false or all group member have right to vote
    var reward: Boolean,
)

enum class BallotStatus {
    OPEN,
    ONGOING,
    FINISHED,
}