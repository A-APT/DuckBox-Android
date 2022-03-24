package com.AligatorAPT.DuckBox.viewmodel.createvote

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateGroupViewModel: ViewModel() {

    val name = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val leader = MutableLiveData<String>()
    val profile = MutableLiveData<Uri>()
    val header = MutableLiveData<Uri>()

    fun setGroupInfo(_name:String, _description:String){
        name.value = _name
        description.value = _description
    }

    fun setLeaderDid(_leader: String){
        leader.value = _leader
    }

    fun setGroupHeader(_header: Uri){
        header.value = _header
    }

    fun setGroupProfile(_profile: Uri){
        profile.value = _profile
    }
}
