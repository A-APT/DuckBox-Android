package com.AligatorAPT.DuckBox.retrofit.callback

import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto

interface SingleGroupCallback {
    fun apiCallback(flag: Boolean, group:GroupDetailDto?)
}
