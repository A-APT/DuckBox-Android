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


class SearchViewModel:ViewModel() {
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO
    //내 그룹 리스트
    var groupList = MutableLiveData<List<GroupDetailDto>?>()

    fun setGroupList(_list: List<GroupDetailDto>){
        groupList.value = _list
    }

    fun searchGroup(query:String, _callback: MyGroupCallback){
        val hashMap = HashMap<String, String>()
        hashMap.put("token", MyApplication.prefs.getString("token", "notExist"))

        viewModelScope.launch {
            withContext(dispatcher){
                GroupModel.searchGroup(
                    _query = query,
                    callback = _callback,
                )
            }
        }
    }
}
