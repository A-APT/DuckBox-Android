package com.AligatorAPT.DuckBox.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto
import com.AligatorAPT.DuckBox.model.GroupModel
import com.AligatorAPT.DuckBox.retrofit.callback.MyGroupCallback
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel:ViewModel() {
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO
    //내 그룹 리스트
    var myGroup = MutableLiveData<List<GroupDetailDto>?>()

    fun setMyGroup(_list: List<GroupDetailDto>){
        myGroup.value = _list
    }

    fun getAllGroup(_callback: MyGroupCallback){
        val hashMap = HashMap<String, String>()
        hashMap.put("token", MyApplication.prefs.getString("token", "notExist"))

        viewModelScope.launch {
            withContext(dispatcher){
                GroupModel.getAllGroup(
                    callback = _callback,
                )
            }
        }
    }
}

object SingletonGroup  {
    private var model: HomeViewModel? = null

    fun getInstance(): HomeViewModel? {
        if (model == null) {
            model = HomeViewModel()
        }
        return model
    }
}
