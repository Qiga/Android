package com.github.dalle.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.dalle.api.UnsplashClient
import com.github.dalle.api.UnsplashService
import com.github.dalle.data.Urls
import com.github.dalle.data.UserImg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    /**
     * HomeFragment에 대한 데이터
     */
    // 이미지 리스트 정보
    private var _imgList: MutableLiveData<MutableList<UserImg>> = MutableLiveData(mutableListOf())
    val imgList: LiveData<MutableList<UserImg>> get() = _imgList!!
    var currentPage = 1

    init {
        setInitImgList()
    }

    private fun setInitImgList() {
        CoroutineScope(Dispatchers.IO).launch {
            _imgList.postValue(UnsplashClient.unsplashService.getLiveUserImg() as MutableList<UserImg>)
        }
    }

    fun addImg(string: String) {
        _imgList.value!!.add(UserImg("id", Urls(regular = string, thumb = string)))
        _imgList.postValue(_imgList.value)
    }

}