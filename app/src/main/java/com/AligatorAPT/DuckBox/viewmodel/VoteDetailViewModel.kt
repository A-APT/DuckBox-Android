package com.AligatorAPT.DuckBox.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VoteDetailViewModel: ViewModel() {
    val isSelected = MutableLiveData<Boolean>()
    val num = MutableLiveData<Int>()

    fun setIsSelected(_isSelected: Boolean){
        isSelected.value = _isSelected
    }

    fun selectNum(_num: Int){
        num.value = _num
    }
}