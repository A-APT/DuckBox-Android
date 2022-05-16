package com.AligatorAPT.DuckBox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AligatorAPT.DuckBox.ethereum.GroupsContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupsContractViewModel : ViewModel(){
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO

    fun registerGroup(groupId: String, ownerDid: String){
        viewModelScope.launch {
            withContext(dispatcher){
                GroupsContract.registerGroup(groupId, ownerDid)
            }
        }
    }

    fun approveGroupAuthentication(groupId: String, approverDid: String){
        viewModelScope.launch {
            withContext(dispatcher){
                GroupsContract.approveGroupAuthentication(groupId, approverDid)
            }
        }
    }

    fun deleteGroup(groupId: String, ownerDid: String){
        viewModelScope.launch {
            withContext(dispatcher){
                GroupsContract.deleteGroup(groupId, ownerDid)
            }
        }
    }

    fun requestMember(groupId: String, userDid: String, name: String, email: String){
        viewModelScope.launch {
            withContext(dispatcher){
                GroupsContract.requestMember(groupId, userDid, name, email)
            }
        }
    }

    fun approveMember(groupId: String, approverDid: String, requesterDid: String){
        viewModelScope.launch {
            withContext(dispatcher){
                GroupsContract.approveMember(groupId, approverDid, requesterDid)
            }
        }
    }

    fun exitMember(groupId: String, requesterDid: String){
        viewModelScope.launch {
            withContext(dispatcher){
                GroupsContract.exitMember(groupId, requesterDid)
            }
        }
    }

    fun getRequesterList(groupId: String){
        viewModelScope.launch {
            withContext(dispatcher){
                GroupsContract.getRequesterList(groupId)
            }
        }
    }

}

object SingletonGroupsContract  {
    private var model: GroupsContractViewModel? = null

    fun getInstance(): GroupsContractViewModel? {
        if (model == null) {
            model = GroupsContractViewModel()
        }
        return model
    }
}
