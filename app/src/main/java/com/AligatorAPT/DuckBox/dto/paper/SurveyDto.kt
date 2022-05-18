package com.AligatorAPT.DuckBox.dto.paper

import com.AligatorAPT.DuckBox.dto.paper.BallotStatus
import com.AligatorAPT.DuckBox.dto.paper.Question
import java.util.*

data class SurveyRegisterDto (
    var title: String,
    var content: String,
    var isGroup: Boolean,
    var groupId: String?, // groupId(ObjectId) if isGroup is true
    var startTime: Date,
    var finishTime: Date,
    var images: List<ByteArray>, // image list
    var ownerPrivate: String, // private key in radix 16
    var questions: List<Question>,
    var targets: List<Int>?, // student id. null if isGroup is false or all group member have right to survey
    var reward: Boolean,
    var notice: Boolean
)