package com.AligatorAPT.DuckBox.view.data

import androidx.lifecycle.MutableLiveData

class ListLiveData<T> : MutableLiveData<ArrayList<T>>() {

    fun add(item: T) {
        val items: ArrayList<T>? = getValue()
        items!!.add(item)
        setValue(items)
    }

    fun addAll(list: List<T>) {
        val items: ArrayList<T>? = getValue()
        items!!.addAll(list)
        setValue(items)
    }

    fun clear(notify: Boolean) {
        val items: ArrayList<T>? = getValue()
        items!!.clear()
        if (notify) {
            setValue(items)
        }
    }

    fun remove(item: T) {
        val items: ArrayList<T>? = getValue()
        items!!.remove(item)
        setValue(items)
    }

    fun notifyChange() {
        val items: ArrayList<T>? = getValue()
        setValue(items)
    }

    init {
        value = ArrayList()
    }
}