package com.github.dalle.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private val _number = MutableLiveData<Int>()
    val number :LiveData<Int> get() = _number

    fun addNumber() {
        Log.d("클릭", _number.value.toString())
        _number.postValue(_number.value?. plus(1) ?: 0)
    }
}