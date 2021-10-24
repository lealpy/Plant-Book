package com.example.plantsbook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsbook.data.models.PlantType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {

    private val _navigationPath = MutableLiveData<NavPath>(NavPath.GardenNavPath)
    val navigationPath: LiveData<NavPath> = _navigationPath

    fun onNavDrawerClicked(navPath: NavPath) {
        _navigationPath.value = navPath
    }

    sealed class NavPath {
        object GardenNavPath : NavPath()
        object LibraryNavPath : NavPath()
    }

}