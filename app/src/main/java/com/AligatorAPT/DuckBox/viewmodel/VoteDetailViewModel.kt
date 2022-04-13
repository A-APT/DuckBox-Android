package com.AligatorAPT.DuckBox.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VoteDetailViewModel: ViewModel() {
    val isSelected = MutableLiveData<Boolean>()

    fun setIsSelected(_isSelected: Boolean){
        isSelected.value = _isSelected
    }
}