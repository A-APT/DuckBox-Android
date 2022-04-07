package com.AligatorAPT.DuckBox.view.data

import android.net.Uri
import java.util.*
import kotlin.collections.ArrayList

data class VoteRegisterDto (
    var title : String,
    var content : String,
    var isGroup : Boolean,
    var groupId: String?, // groupId(ObjectId) if isGroup is true
    var startTime : String,
    var finishTime : String,
    var images : ArrayList<ByteArray>?,
    var candidates : ArrayList<String>?,
    var voters : ArrayList<String>?,
    var reward : Boolean,
    var notice : Boolean,
)

data class VoteDetailDto(
    val id: String, // ObjectId
    var title: String,
    var content: String,
    var isGroup: Boolean,
    var groupId: String?, // groupId(ObjectId) if isGroup is true
    var owner: String, // owner's nicknam
    var startTime: Date,
    var finishTime: Date,
    var status: BallotStatus,
    var images: List<ByteArray>, // image list
    var candidates: List<String>,
    var reward: Boolean,
)

enum class BallotStatus {
    OPEN,
    ONGOING,
    FINISHED,
}