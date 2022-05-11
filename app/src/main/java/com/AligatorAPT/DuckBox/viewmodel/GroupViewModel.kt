package com.AligatorAPT.DuckBox.viewmodel

import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AligatorAPT.DuckBox.dto.group.GroupStatus
import com.AligatorAPT.DuckBox.dto.group.GroupUpdateDto
import com.AligatorAPT.DuckBox.model.GroupModel
import com.AligatorAPT.DuckBox.model.UserModel
import com.AligatorAPT.DuckBox.retrofit.callback.ApiCallback
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupViewModel: ViewModel() {
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO

    enum class Authority{
        MASTER,
        MEMBER,
        OTHER
    }

    //그룹 가입정보
    val name = MutableLiveData<String>()
    var leader = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var profile = MutableLiveData<ByteArray?>()
    var header = MutableLiveData<ByteArray?>()

    //그룹 기본 정보
    var id = MutableLiveData<String>()
    var status = MutableLiveData<GroupStatus>() // 그룹 인증

    //그룹 권한
    var authority = MutableLiveData<Authority>()

    fun setGroupInfo(
        _name:String,
        _leader:String,
        _description:String,
        _profile:String?,
        _header:String?,
        _id:String,
        _status:GroupStatus){

        val profileByte: ByteArray = Base64.decode(_profile, Base64.DEFAULT)
        val headerByte: ByteArray = Base64.decode(_header, Base64.DEFAULT)

        name.value = _name
        leader.value = _leader
        description.value = _description
        profile.value = profileByte
        header.value = headerByte
        id.value = _id
        status.value = _status
    }

    fun setAuthority(_authority: Authority){
        authority.value = _authority
    }

    fun updateGroupInfo(
        _description: String,
        _profile: ByteArray?,
        _header: ByteArray?
    ){
        description.value = _description
        profile.value = _profile
        header.value = _header
    }

    fun joinGroup(_callback: ApiCallback){
        viewModelScope.launch {
            withContext(dispatcher){
                UserModel.joinGroup(
                    _groupId = id.value!!,
                    callback = _callback,
                )
            }
        }
    }

    fun updateGroup(_callback: ApiCallback){
        viewModelScope.launch {
            withContext(dispatcher){
                GroupModel.updateGroup(
                    _groupUpdateDto = GroupUpdateDto(
                        description = description.value,
                        header = header.value,
                        id = id.value!!,
                        profile = profile.value
                    ),
                    callback = _callback,
                )
            }
        }
    }
}
