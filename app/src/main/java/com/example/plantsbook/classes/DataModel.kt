package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsbook.classes.Plant

open class DataModel : ViewModel() {

    val plantListLiveData : MutableLiveData<MutableList<Plant>> by lazy {
        MutableLiveData<MutableList<Plant>>()
    }

    val plantLiveData : MutableLiveData<Plant> by lazy {
        MutableLiveData<Plant>()
    }

}