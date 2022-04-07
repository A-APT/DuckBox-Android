package com.AligatorAPT.DuckBox.callback

import com.AligatorAPT.DuckBox.view.data.VoteDetailDto

interface VoteCallback {
    fun apiCallback(flag: Boolean, _list: List<VoteDetailDto>?)
}