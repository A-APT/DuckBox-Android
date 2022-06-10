package com.AligatorAPT.DuckBox.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AligatorAPT.DuckBox.dto.paper.VoteDetailDto
import com.AligatorAPT.DuckBox.dto.user.BlindSigRequestDto
import com.AligatorAPT.DuckBox.model.VoteModel
import com.AligatorAPT.DuckBox.retrofit.callback.TokenCallback
import com.AligatorAPT.DuckBox.retrofit.callback.VoteCallback
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

    fun generateVoteToken(blindSigRequestDto: BlindSigRequestDto, _callback: TokenCallback){
        viewModelScope.launch {
            withContext(dispatcher){
                VoteModel.generateVoteToken(
                    blindSigRequestDto = blindSigRequestDto,
                    callback = _callback
                )
            }
        }
    }

    object VoteSingletonGroup  {
        private var model: VoteViewModel? = null

        fun getInstance(): VoteViewModel? {
            if (model == null) {
                model = VoteViewModel()
            }
            return model
        }
    }
}