package com.AligatorAPT.DuckBox.view.data

data class PaperListData(
    var image:Int,
    var title:String,
    var writer:String,
    var isVote:Boolean,
    var canParticipate:Boolean,
    var time:String,
    var totalMember:Int,
    var joinMember:Int,
    ){
}
