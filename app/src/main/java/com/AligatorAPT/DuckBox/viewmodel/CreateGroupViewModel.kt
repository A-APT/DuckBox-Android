package com.AligatorAPT.DuckBox.viewmodel.createvote

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AligatorAPT.DuckBox.dto.group.GroupRegisterDto
import com.AligatorAPT.DuckBox.model.GroupModel
import com.AligatorAPT.DuckBox.retrofit.callback.ApiCallback
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.OutputStream

class CreateGroupViewModel: ViewModel() {
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO

    val name = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val leader = MutableLiveData<String>()
    val profile = MutableLiveData<Bitmap?>()
    val header = MutableLiveData<Bitmap?>()

    fun setGroupInfo(_name:String, _description:String){
        name.value = _name
        description.value = _description
    }

    fun setLeaderDid(_leader: String){
        leader.value = _leader
    }

    fun setGroupHeader(_header: Bitmap){
        header.value = _header
    }

    fun setGroupProfile(_profile: Bitmap){
        profile.value = _profile
    }

    fun register(callback: ApiCallback){
        Log.d("IMAGE", profile.value.toString())
        val profileByteArray: OutputStream? = ByteArrayOutputStream()
        profile.value?.compress(Bitmap.CompressFormat.JPEG, 2, profileByteArray)
        Log.d("IMAGEBITMAP", profileByteArray.toString().toByteArray().toString())

        val headerByteArray: OutputStream? = ByteArrayOutputStream()
        header.value?.compress(Bitmap.CompressFormat.JPEG, 2, headerByteArray)

        viewModelScope.launch {
            withContext(dispatcher){
                GroupModel.register(
                    _groupRegisterDto = GroupRegisterDto(
                        name = name.value!!,
                        leader = MyApplication.prefs.getString("did", "notExist"),
                        description = description.value!!,
                        profile = profile.toString().toByteArray(),
                        header = headerByteArray.toString().toByteArray(),
                    ),
                    callback
                )
            }
        }
    }

}
