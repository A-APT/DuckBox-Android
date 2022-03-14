package com.AligatorAPT.DuckBox.view.data

import android.net.Uri

data class CreatVoteData (
    var title : String,
    var content : String,
    var StartDate : String,
    var LastDate : String,
    var Image : ArrayList<Uri>,
    var type : String,
    var list : ArrayList<String>,
    var excelList : ArrayList<ExcelData>,
    var alarm : Boolean,
    var reward : Boolean
)

data class ExcelData(
    var name: String,
    var studentID: String
)