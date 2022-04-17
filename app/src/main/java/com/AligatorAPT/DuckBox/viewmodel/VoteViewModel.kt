package com.AligatorAPT.DuckBox.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto
import com.AligatorAPT.DuckBox.model.VoteModel
import com.AligatorAPT.DuckBox.retrofit.callback.VoteCallback
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import com.AligatorAPT.DuckBox.view.data.VoteDetailDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VoteViewModel: ViewModel() {
    private var dispatcher: CoroutineDispatcher = kotlinx.coroutines.Dispatchers.IO

    var myVote = MutableLiveData<List<VoteDetailDto>?>()

    fun setMyVote(_list: List<VoteDetailDto>){
        myVote.value = _list
    }

    fun getAllVote(_callback: VoteCallback){
        viewModelScope.launch{
            withContext(dispatcher){
                VoteModel.getAllVote(
                    callback = _callback
                )
            }
        }
    }

    object SingletonGroup  {
        private var model: VoteViewModel? = null

        fun getInstance(): VoteViewModel? {
            if (model == null) {
                model = VoteViewModel()
            }
            return model
        }
    }
}