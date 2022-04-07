package com.AligatorAPT.DuckBox.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

class CreateVoteViewModel : ViewModel() {

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val isGroup = MutableLiveData<Boolean>()
    val owner = MutableLiveData<String>()
    val startTime = MutableLiveData<String>()
    val finishTime = MutableLiveData<String>()
    val images = MutableLiveData<ArrayList<ByteArray>?>()
    val candidates = MutableLiveData<ArrayList<String>?>()
    val voters = MutableLiveData<ArrayList<String>?>()
    val reward = MutableLiveData<Boolean>()
    val notice = MutableLiveData<Boolean>()

    val data get() = candidates

    fun setVoteFirst(_title: String, _content: String, _startTime: String, _finalTime: String){
        title.value = _title
        content.value = _content
        startTime.value = _startTime
        finishTime.value = _finalTime
//        images.value = _images
    }

    fun setCandidateData(arr: ArrayList<String>){
        candidates.value = arr
    }

    fun setVoters(_voters: ArrayList<String>?){
        voters.value = _voters
    }

    fun setReward(_reward: Boolean){
        reward.value = _reward
    }

    fun setNotice(_notice: Boolean){
        notice.value = _notice
    }
}