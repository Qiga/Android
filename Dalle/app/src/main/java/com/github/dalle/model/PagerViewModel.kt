package com.github.dalle.model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class PagerViewModel : ViewModel() {
    // 이미지 리스트 정보
    private var _imgList : MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf(
        "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg",
        "https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
        "https://cdn.pixabay.com/photo/2020/03/08/21/41/landscape-4913841_1280.jpg",
        "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
        "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"
    ))

    val imgList : LiveData<MutableList<String>> get() = _imgList!!

    fun addImg(string: String){
        _imgList.value?.add(string)
    }
}