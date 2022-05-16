package com.AligatorAPT.DuckBox.viewmodel.createvote

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AligatorAPT.DuckBox.dto.group.GroupRegisterDto
import com.AligatorAPT.DuckBox.ethereum.GroupsContract
import com.AligatorAPT.DuckBox.model.GroupModel
import com.AligatorAPT.DuckBox.retrofit.callback.RegisterCallBack
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class CreateGroupViewModel : ViewModel() {
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO

    val name = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val leader = MutableLiveData<String>()
    val profile = MutableLiveData<Bitmap?>()
    val header = MutableLiveData<Bitmap?>()
    val id = MutableLiveData<String>()

    fun setGroupInfo(_name: String, _description: String) {
        name.value = _name
        description.value = _description
    }

    fun setLeaderDid(_leader: String) {
        leader.value = _leader
    }

    fun setGroupHeader(_header: Bitmap) {
        header.value = _header
    }

    fun setGroupProfile(_profile: Bitmap) {
        profile.value = _profile
    }

    fun setGroupId(_id: String) {
        id.value = _id
    }

    fun register(callback: RegisterCallBack) {
        val profileByteArray: ByteArrayOutputStream? = ByteArrayOutputStream()
        profile.value?.compress(Bitmap.CompressFormat.PNG, 2, profileByteArray)

        val headerByteArray: ByteArrayOutputStream? = ByteArrayOutputStream()
        header.value?.compress(Bitmap.CompressFormat.PNG, 2, headerByteArray)

        viewModelScope.launch {
            withContext(dispatcher) {
                GroupModel.register(
                    _groupRegisterDto = GroupRegisterDto(
                        name = name.value!!,
                        leader = MyApplication.prefs.getString("did", "notExist"),
                        description = description.value!!,
                        profile = profileByteArray?.toByteArray(),
                        header = headerByteArray?.toByteArray(),
                    ),
                    callback
                )
            }
        }
    }

    fun registerGroupContract(groupId: String, ownerDid: String) {
        viewModelScope.launch {
            withContext(dispatcher) {
                GroupsContract.registerGroup(groupId, ownerDid)
            }
        }
    }

}
