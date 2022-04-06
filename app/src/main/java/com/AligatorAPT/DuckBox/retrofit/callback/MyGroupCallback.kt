package com.AligatorAPT.DuckBox.retrofit.callback

import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto

interface MyGroupCallback {
    fun apiCallback(flag: Boolean, _list:List<GroupDetailDto>?)
}
