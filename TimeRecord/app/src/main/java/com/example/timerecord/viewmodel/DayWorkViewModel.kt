package com.example.timerecord.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timerecord.data.Work
import com.example.timerecord.repository.DayWorkRepository
import java.util.Date

class DayWorkViewModel : ViewModel() {

    private val workRepository : DayWorkRepository = DayWorkRepository()

    private var _workList : MutableLiveData<MutableList<Work>> = MutableLiveData(mutableListOf())
    private val workList get() = _workList!!

    fun getDayWorks(selectedDate : Date){
        val resultList = workRepository.getWorks(Date(20171229))
        _workList.postValue(resultList)
    }

}