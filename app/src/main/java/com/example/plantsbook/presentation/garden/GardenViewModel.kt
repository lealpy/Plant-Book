package com.example.plantsbook.presentation.garden

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsbook.data.models.Plant
import com.example.plantsbook.data.models.PlantState
import com.example.plantsbook.data.repository.PlantsRepository
import com.example.plantsbook.databinding.FragmentGardenBinding
import com.example.plantsbook.databinding.PlantItemBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GardenViewModel @Inject constructor(
    private val plantsRepository: PlantsRepository
) : ViewModel() {

    val plantList = plantsRepository.plantList

    //////////////////////////////////////////////////////////////////////////////////////
    private lateinit var deletedPlant : Plant
    //////////////////////////////////////////////////////////////////////////////////////

    fun addRandomPlant() {
        val plant = plantsRepository.getRandomPlant()
        plantsRepository.addPlant(plant)
    }

    fun nextDay() {
        val listOfNonTriggeredPlants = plantList.value ?: mutableListOf<Plant>()

        listOfNonTriggeredPlants.removeAll{plant ->
            plant.state.isTriggered()
        }

        listOfNonTriggeredPlants.forEach { plant ->
            plant.state = PlantState(
                isNotWatered = (Math.random() < PROBABILITY_IS_NOT_WATERED),
                isLeavesFallen = (Math.random() < PROBABILITY_IS_LEAVES_FALLEN),
                isInsectsAttacked = (Math.random() < PROBABILITY_IS_INSECTS_ATTACKED)
            )
        }
        plantsRepository.setPlantList(listOfNonTriggeredPlants)
    }

    fun removePlant (position : Int) {
        val __plantList = plantList.value ?: mutableListOf<Plant>()
        deletedPlant = __plantList?.get(position)
        __plantList?.removeAt(position)
        plantsRepository.setPlantList(__plantList)
    }

    fun returnRemovedPlant (position: Int) {
        val __plantList = plantList.value ?: mutableListOf<Plant>()
        __plantList.add(position, deletedPlant)
        plantsRepository.setPlantList(__plantList)
    }

    companion object {
        const val PROBABILITY_IS_NOT_WATERED = 0.2
        const val PROBABILITY_IS_LEAVES_FALLEN = 0.05
        const val PROBABILITY_IS_INSECTS_ATTACKED = 0.05
    }

}