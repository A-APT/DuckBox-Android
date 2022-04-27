package com.AligatorAPT.DuckBox.view.data

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

data class VoteRegisterDto(
    var title: String,
    var content: String,
    var isGroup: Boolean,
    var groupId: String?,
    var startTime: Date,
    var finishTime: Date,
    var images: List<ByteArray>,
    var candidates: List<String>,
    var voters: List<Int>?,
    var reward: Boolean,
    var notice: Boolean,
)

data class VoteDetailDto(
    val id: String, // ObjectId
    var title: String,
    var content: String,
    var group: Boolean,
    var groupId: String?, // groupId(ObjectId) if isGroup is true
    var owner: String, // owner's nicknam
    var startTime: Date,
    var finishTime: Date,
    var status: BallotStatus,
    var images: List<String>, // image list
    var candidates: List<String>,
    var reward: Boolean,
):Serializable

enum class BallotStatus {
    REGISTERED,
    OPEN,
    FINISHED,
}