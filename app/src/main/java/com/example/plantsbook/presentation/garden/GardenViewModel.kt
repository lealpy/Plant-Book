package com.example.plantsbook.presentation.garden

import androidx.lifecycle.ViewModel
import com.example.plantsbook.data.models.PlantState
import com.example.plantsbook.data.repository.PlantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GardenViewModel @Inject constructor(
    private val plantsRepository: PlantsRepository
) : ViewModel() {

    val plantList = plantsRepository.plantList

    fun addRandomPlant() {
        val plant = plantsRepository.getRandomPlant()
        plantsRepository.addPlant(plant)
    }

    fun nextDay() {
        val listOfNonTriggeredPlants = plantList.value?.filter { plant ->
            !plant.state.isTriggered()
        } ?: emptyList()

        listOfNonTriggeredPlants.forEach { plant ->
            plant.state = PlantState(
                isNotWatered = (Math.random() < PROBABILITY_IS_NOT_WATERED),
                isLeavesFallen = (Math.random() < PROBABILITY_IS_LEAVES_FALLEN),
                isInsectsAttacked = (Math.random() < PROBABILITY_IS_INSECTS_ATTACKED)
            )
        }

        plantsRepository.setPlants(listOfNonTriggeredPlants)
    }

    companion object {
        const val PROBABILITY_IS_NOT_WATERED = 0.2
        const val PROBABILITY_IS_LEAVES_FALLEN = 0.05
        const val PROBABILITY_IS_INSECTS_ATTACKED = 0.05
    }

}