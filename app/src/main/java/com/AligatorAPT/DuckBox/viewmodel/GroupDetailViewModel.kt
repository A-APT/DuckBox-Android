package com.AligatorAPT.DuckBox.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GroupDetailViewModel: ViewModel() {
    enum class Authority{
        MASTER,
        MEMBER,
        OTHER
    }

    //그룹 가입정보
    val name = MutableLiveData<String>()
    var leader = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var profile = MutableLiveData<String>()
    var header = MutableLiveData<String>()

    //그룹 기본 정보
    var id = MutableLiveData<String>()
    var status = MutableLiveData<String>() // 그룹 인증

    //그룹 권한
    var authority = MutableLiveData<Authority>()

    fun setGroupInfo(
        _name:String,
        _leader:String,
        _description:String,
        _profile:String,
        _header:String,
        _id:String,
        _status:String){

        name.value = _name
        leader.value = _leader
        description.value = _description
        profile.value = _profile
        header.value = _header
        id.value = _id
        status.value = _status
    }

    fun setAuthority(_authority: Authority){
        authority.value = _authority
    }

}