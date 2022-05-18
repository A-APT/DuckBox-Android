package com.AligatorAPT.DuckBox.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import androidx.lifecycle.viewModelScope
import com.AligatorAPT.DuckBox.dto.paper.Question
import com.AligatorAPT.DuckBox.dto.paper.SurveyRegisterDto
import com.AligatorAPT.DuckBox.dto.paper.VoteRegisterDto
import com.AligatorAPT.DuckBox.model.SurveyModel
import com.AligatorAPT.DuckBox.model.VoteModel
import com.AligatorAPT.DuckBox.retrofit.callback.RegisterCallBack
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateVoteViewModel : ViewModel() {
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val isGroup = MutableLiveData<Boolean>()
    val groupId = MutableLiveData<String?>()
    val startTime = MutableLiveData<Date>()
    val finishTime = MutableLiveData<Date>()
    val images = MutableLiveData<ArrayList<ByteArray>>()
    val ownerPrivate = MutableLiveData<String>()
    val voters = MutableLiveData<ArrayList<Int>?>() // 유권자 리스트
    val reward = MutableLiveData<Boolean>()
    val notice = MutableLiveData<Boolean>()

    //vote
    val candidates = MutableLiveData<ArrayList<String>>()   //후보자 리스트

    //survey
    val questions = MutableLiveData<ArrayList<Question>>()  // 설문 리스트
    val result = MutableLiveData<Boolean>() // 설문 결과 공개 여부

    val data get() = candidates
    val questionData get() = questions
    var isVote = MutableLiveData<Boolean>()

    fun setFirst(_title: String, _content: String, _startTime: Date, _finalTime: Date, _images: ArrayList<ByteArray>){
        title.value = _title
        content.value = _content
        startTime.value = _startTime
        finishTime.value = _finalTime
        images.value = _images
        Log.e("viewmodel_list",images.value.toString())
    }

    fun setCandidateData(arr: ArrayList<String>){
        candidates.value = arr
    }

    fun setVoters(_voters: ArrayList<Int>?){
        voters.value = _voters
    }

    fun setReward(_reward: Boolean){
        reward.value = _reward
    }

    fun setNotice(_notice: Boolean){
        notice.value = _notice
    }

    fun setGroup(_isGroup: Boolean, _groupId: String?){
        isGroup.value = _isGroup
        groupId.value = _groupId
    }

    fun setQuestionsData(arr: ArrayList<Question>){
        questions.value = arr
    }

    fun setResult(_result: Boolean){
        result.value = _result
    }

    fun registerVote(callback: RegisterCallBack){

        viewModelScope.launch {
            withContext(dispatcher){
                val data = VoteRegisterDto(
                    title = title.value!!,
                    content = content.value!!,
                    isGroup = isGroup.value!!,
                    groupId = groupId.value,
                    startTime = startTime.value!!,
                    finishTime = finishTime.value!!,
                    images = images.value!!,
                    ownerPrivate = ownerPrivate.value!!,
                    candidates = candidates.value!!,
                    voters = voters.value,
                    reward = reward.value!!,
                    notice = notice.value!!
                )
                Log.e("VOTEDATA",data.toString())
                VoteModel.registerVote(data, callback)
            }
        }
    }

    fun registerSurvey(callback: RegisterCallBack){

        viewModelScope.launch {
            withContext(dispatcher){
                val data = SurveyRegisterDto(
                    title = title.value!!,
                    content = content.value!!,
                    isGroup = isGroup.value!!,
                    groupId = groupId.value,
                    startTime = startTime.value!!,
                    finishTime = finishTime.value!!,
                    images = images.value!!,
                    ownerPrivate = ownerPrivate.value!!,
                    questions = questions.value!!,
                    targets = voters.value,
                    reward = reward.value!!,
                    notice = notice.value!!
                )
                Log.e("SURVEYDATA",data.toString())
                SurveyModel.registerSurvey(data, callback)
            }
        }
    }
}