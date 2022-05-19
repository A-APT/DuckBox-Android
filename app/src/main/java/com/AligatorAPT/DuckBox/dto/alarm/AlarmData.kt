package com.AligatorAPT.DuckBox.dto.alarm

import java.util.*

data class AlarmData(
    val groupName: String,
    val content: String,
    val time: Date,
    val isNew: Boolean
){}
