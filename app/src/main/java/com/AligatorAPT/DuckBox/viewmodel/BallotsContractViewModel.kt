package com.AligatorAPT.DuckBox.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AligatorAPT.DuckBox.dto.ethereum.BallotData
import com.AligatorAPT.DuckBox.dto.ethereum.VoteData
import com.AligatorAPT.DuckBox.ethereum.BallotContract
import com.AligatorAPT.DuckBox.retrofit.callback.ApiCallback
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger

class BallotsContractViewModel : ViewModel(){
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO

    var resultList = MutableLiveData<List<BigInteger>?>()

    fun registerBallot(_ballotData: BallotData){
        viewModelScope.launch {
            withContext(dispatcher){
                BallotContract.registerBallot(_ballotData)
            }
        }
    }

    fun vote(voteData: VoteData, callBack:ApiCallback){
        viewModelScope.launch {
            withContext(dispatcher){
                BallotContract.vote(voteData, callBack)
            }
        }
    }

    fun resultOfBallot(ballotId: String){
        viewModelScope.launch {
            withContext(dispatcher){
                resultList.postValue(BallotContract.resultOfBallot(ballotId))
            }
        }
    }

}

object SingletonBallotsContract  {
    private var model: BallotsContractViewModel? = null

    fun getInstance(): BallotsContractViewModel? {
        if (model == null) {
            model = BallotsContractViewModel()
        }
        return model
    }
}