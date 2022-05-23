package com.AligatorAPT.DuckBox.retrofit.callback

import com.AligatorAPT.DuckBox.dto.paper.VoteDetailDto
import com.AligatorAPT.DuckBox.dto.user.BlindSigToken

interface TokenCallback {
    fun tokenCallback(flag: Boolean, _blindsigToken: BlindSigToken?)
}