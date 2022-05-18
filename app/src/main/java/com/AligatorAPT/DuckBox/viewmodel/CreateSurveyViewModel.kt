package com.AligatorAPT.DuckBox.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AligatorAPT.DuckBox.dto.vote.Question
import com.AligatorAPT.DuckBox.dto.vote.SurveyRegisterDto
import com.AligatorAPT.DuckBox.dto.vote.VoteRegisterDto
import com.AligatorAPT.DuckBox.model.SurveyModel
import com.AligatorAPT.DuckBox.model.VoteModel
import com.AligatorAPT.DuckBox.retrofit.callback.RegisterCallBack
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CreateSurveyViewModel: ViewModel() {
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val isGroup = MutableLiveData<Boolean>()
    val groupId = MutableLiveData<String?>()
    val startTime = MutableLiveData<Date>()
    val finishTime = MutableLiveData<Date>()
    val images = MutableLiveData<ArrayList<ByteArray>>()
    val ownerPrivate = MutableLiveData<String>()
    val questions = MutableLiveData<ArrayList<Question>>()
    val targets = MutableLiveData<ArrayList<Int>?>()
    val reward = MutableLiveData<Boolean>()
    val notice = MutableLiveData<Boolean>()

    fun setSurveyFirst(_title: String, _content: String, _startTime: Date, _finalTime: Date, _images: ArrayList<ByteArray>){
        title.value = _title
        content.value = _content
        startTime.value = _startTime
        finishTime.value = _finalTime
        images.value = _images
        Log.e("viewmodel_list",images.value.toString())
    }

    fun setQuestionsData(arr: ArrayList<Question>){
        questions.value = arr
    }

    fun setTargets(_targets: ArrayList<Int>?){
        targets.value = _targets
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

    fun registerSurvey(callback: RegisterCallBack){

        viewModelScope.launch {
            withContext(dispatcher){
                SurveyModel.registerSurvey(
                    _surveyRegisterDto = SurveyRegisterDto(
                        title = title.value!!,
                        content = content.value!!,
                        isGroup = isGroup.value!!,
                        groupId = groupId.value,
                        startTime = startTime.value!!,
                        finishTime = finishTime.value!!,
                        images = images.value!!,
                        ownerPrivate = ownerPrivate.value!!,
                        questions = questions.value!!,
                        targets = targets.value,
                        reward = reward.value!!,
                        notice = notice.value!!
                    ),
                    callback
                )
            }
        }
    }
}