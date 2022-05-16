package com.AligatorAPT.DuckBox.view.data

data class PaperListData(
    var image:ByteArray,
    var title:String,
    var writer:String,
    var isVote:Boolean,
    var canParticipate:Boolean,
    var time:String
    ){
}
