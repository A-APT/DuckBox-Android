package com.AligatorAPT.DuckBox.retrofit.callback

import com.AligatorAPT.DuckBox.view.data.VoteDetailDto

interface VoteCallback {
    fun apiCallback(flag: Boolean, _list: ArrayList<VoteDetailDto>?)
}