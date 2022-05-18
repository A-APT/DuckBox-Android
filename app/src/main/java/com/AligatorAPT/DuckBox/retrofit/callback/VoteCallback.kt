package com.AligatorAPT.DuckBox.retrofit.callback

import com.AligatorAPT.DuckBox.dto.vote.VoteDetailDto

interface VoteCallback {
    fun apiCallback(flag: Boolean, _list: List<VoteDetailDto>?)
}