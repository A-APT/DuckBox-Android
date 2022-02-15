package com.AligatorAPT.DuckBox.view.data

data class VoteAndPollListData(
    var image:Int,
    var title:String,
    var groupName:String,
    var isVote:Boolean,
    var canParticipate:Boolean,
    var time:String,
    var ratio:String,
    var numOfPeople:String){
}
