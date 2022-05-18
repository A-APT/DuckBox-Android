package com.AligatorAPT.DuckBox.view.data

import java.util.*

data class AlarmData(
    val groupName: String,
    val content: String,
    val time: Date,
    val isNew: Boolean
){}
