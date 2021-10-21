package com.example.plantsbook.presentation.plant_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantsbook.data.models.Plant
import com.example.plantsbook.data.models.PlantType
import com.example.plantsbook.data.repository.PlantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantInfoViewModel @Inject constructor(
    private val plantsRepository: PlantsRepository
) : ViewModel() {

    private val _plantInfo = MutableLiveData<Plant>(null)
    val plantInfo: LiveData<Plant> = _plantInfo

    fun fetchPlantInfo(typeOrdinal: Int) {
        val type = PlantType.values()[typeOrdinal]
        val plant = plantsRepository.getPlantByType(type)
        _plantInfo.value = plant
    }

    fun addPlant() {
        _plantInfo.value?.let { plant ->
            plantsRepository.addPlant(plant)
        }
    }

}