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

    private val _navigationPath = MutableLiveData<NavPath>(NavPath.GardenNavPath(null))
    val navigationPath: LiveData<NavPath> = _navigationPath

    fun onNavDrawerClicked() {
        _navigationPath.value = NavPath.GardenNavPath(null)
    }

    fun onNavDrawerClicked(plantType: PlantType) {
        _navigationPath.value = NavPath.PlantInfoNavPath(plantType)
    }

    sealed class NavPath(plantType: PlantType?) {
        data class GardenNavPath(val plantType: PlantType?) : NavPath(plantType)
        data class PlantInfoNavPath(val plantType: PlantType?) : NavPath(plantType)
    }

}