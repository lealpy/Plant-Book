package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsbook.classes.Plant

open class DataModel : ViewModel() {
    val messageForActivity : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val messageForGardenFragment : MutableLiveData<List<Plant>> by lazy {
        MutableLiveData<List<Plant>>()
    }

    val messageForPlantInfoFragment : MutableLiveData<Plant> by lazy {
        MutableLiveData<Plant>()
    }

}