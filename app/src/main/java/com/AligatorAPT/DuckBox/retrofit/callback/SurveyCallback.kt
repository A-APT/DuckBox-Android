package com.AligatorAPT.DuckBox.retrofit.callback

import com.AligatorAPT.DuckBox.dto.paper.SurveyDetailDto

interface SurveyCallback {
    fun apiCallback(flag: Boolean, _list: List<SurveyDetailDto>?)
}