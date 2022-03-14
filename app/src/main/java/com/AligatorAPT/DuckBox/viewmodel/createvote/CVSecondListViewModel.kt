package com.AligatorAPT.DuckBox.viewmodel.createvote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

class CVSecondListViewModel : ViewModel() {

    private val etLiveData = MutableLiveData<ArrayList<String>?>()

    val data get() = etLiveData

    fun addTask(item : ArrayList<String>){
        etLiveData.value = item
    }

    fun deleteTask(p : Int){
        etLiveData.value?.removeAt(p)
        etLiveData.value = etLiveData.value
    }

    fun swapTask(from: Int, to:Int){
        Collections.swap(etLiveData.value, from, to)
        etLiveData.value = etLiveData.value
    }

    fun set(p: Int, s:String){
        etLiveData.value?.set(p,s)
        etLiveData.value = etLiveData.value
    }
}