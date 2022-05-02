package com.AligatorAPT.DuckBox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AligatorAPT.DuckBox.ethereum.GroupsContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestViewModel : ViewModel(){
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO

    fun registerGroup(groupId: String, ownerDid: String){
        viewModelScope.launch {
            withContext(dispatcher){
                GroupsContract.registerGroup(groupId, ownerDid)
            }
        }
    }

}