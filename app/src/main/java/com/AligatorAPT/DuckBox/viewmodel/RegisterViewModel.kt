package com.AligatorAPT.DuckBox.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AligatorAPT.DuckBox.dto.user.EmailTokenDto
import com.AligatorAPT.DuckBox.dto.user.RegisterDto
import com.AligatorAPT.DuckBox.model.EmailModel
import com.AligatorAPT.DuckBox.model.UserModel
import com.AligatorAPT.DuckBox.retrofit.callback.ApiCallback
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel: ViewModel(){
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO

    val email = MutableLiveData<String>()
    val nickname = MutableLiveData<String>()

    val isNew = MutableLiveData<Boolean>()

    fun setIsNew(_isNew: Boolean){
        isNew.value = _isNew
    }

    fun setEmail(_email:String){
        email.value = _email
    }

    fun setNickname(_nickname:String){
        nickname.value = _nickname
    }

    fun generateEmailAuth(_email: String, callback: ApiCallback){
        viewModelScope.launch {
            withContext(dispatcher){
                EmailModel.generateEmailAuth(_email, callback)
            }
        }
    }

    fun verifyEmailToken(_emailTokenDto: EmailTokenDto, callback: ApiCallback){
        viewModelScope.launch {
            withContext(dispatcher){
                EmailModel.verifyEmailToken(_emailTokenDto, callback)
            }
        }
    }

    fun registser(_userInfo: RegisterDto, callback: ApiCallback){
        viewModelScope.launch {
            withContext(dispatcher){
                UserModel.register(_userInfo, callback)
            }
        }
    }
}
