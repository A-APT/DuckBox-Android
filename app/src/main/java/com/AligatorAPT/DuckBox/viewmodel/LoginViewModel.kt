package com.AligatorAPT.DuckBox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AligatorAPT.DuckBox.dto.user.LoginRequestDto
import com.AligatorAPT.DuckBox.model.UserModel
import com.AligatorAPT.DuckBox.retrofit.callback.ApiCallback
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel: ViewModel() {
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO

    fun login(_loginRequestDto: LoginRequestDto, callback: ApiCallback){
        viewModelScope.launch {
            withContext(dispatcher){
                UserModel.login(_loginRequestDto, callback)
            }
        }
    }
}
