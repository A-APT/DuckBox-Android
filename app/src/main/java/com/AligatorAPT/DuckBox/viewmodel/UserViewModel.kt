package com.AligatorAPT.DuckBox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.AligatorAPT.DuckBox.ethereum.DIDContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel : ViewModel() {
    // TAG
    private val logTag: String = this::class.java.simpleName

    private var dispatcher: CoroutineDispatcher = Dispatchers.IO

    /* temporary function */
    fun register() {
        viewModelScope.launch {
            withContext(dispatcher) {
                DIDContract.registerDid("did") // at server?
            }
        }
    }

}