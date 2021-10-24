package com.example.plantsbook.presentation.plant_state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsbook.data.models.Plant
import com.example.plantsbook.data.repository.PlantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlantStateViewModel @Inject constructor(
    private val plantsRepository: PlantsRepository
) : ViewModel() {

    private val _plant = MutableLiveData<Plant>()
    val plant: LiveData<Plant> = _plant

    fun fetchPlant(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val plant = plantsRepository.getPlant(id)
                withContext(Dispatchers.Main) {
                    _plant.value = plant
                }
            }
        }
    }

}