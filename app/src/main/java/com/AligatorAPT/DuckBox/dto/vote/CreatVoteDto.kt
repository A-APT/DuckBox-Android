package com.AligatorAPT.DuckBox.view.data

import android.net.Uri

data class CreatVoteData (
    var title : String,
    var content : String,
    var isGroup : Boolean,
    var owner: String,
    var startTime : String,
    var finishTime : String,
    var images : ArrayList<Uri>,
    var candidates : ArrayList<String>,
    var voters : ArrayList<ExcelData>,
    var reward : Boolean,
    var notice : Boolean,
)

data class ExcelData(
    var studentID: String
)