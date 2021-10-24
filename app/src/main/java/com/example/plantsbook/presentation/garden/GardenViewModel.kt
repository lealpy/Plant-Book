package com.example.plantsbook.presentation.garden

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsbook.data.models.Plant
import com.example.plantsbook.data.models.PlantState
import com.example.plantsbook.data.repository.PlantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GardenViewModel @Inject constructor(
    private val plantsRepository: PlantsRepository
) : ViewModel() {

    private var deletedPlant: Plant? = null

    fun getPlantsLd(): LiveData<List<Plant>> {
        return plantsRepository.getPlantsLD()
    }

    fun insertRandomPlant() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                plantsRepository.insertRandomPlant()
            }
        }
    }

    fun deletePlant(plant: Plant) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                plantsRepository.deletePlant(plant)
            }
        }
    }

    fun nextDay() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // Берем все растения из БД
                val plants = plantsRepository.getPlants().toMutableList()

                // Формируем список тех, которые померли и их нужно удалить
                val plantsToRemove = plants.filter { plant ->
                    plant.state.isTriggered()
                }
                plants.removeAll(plantsToRemove)

                // Формируем список тех, которые изменили свое состояние и их нужно обновить
                val plantsToUpdate = mutableListOf<Plant>()
                plants.forEach { plant ->
                    val isNotWatered = Math.random() < PROBABILITY_IS_NOT_WATERED
                    val isLeavesFallen = Math.random() < PROBABILITY_IS_LEAVES_FALLEN
                    val isInsectsAttacked = Math.random() < PROBABILITY_IS_INSECTS_ATTACKED
                    if (isNotWatered || isLeavesFallen || isInsectsAttacked) {
                        plant.state = PlantState(
                            isNotWatered = isNotWatered,
                            isLeavesFallen = isLeavesFallen,
                            isInsectsAttacked = isInsectsAttacked
                        )
                        plantsToUpdate.add(plant)
                    }
                }

                // Удаляем из БД те, что надо было удалить
                plantsToRemove.forEach { plant ->
                    plantsRepository.deletePlant(plant)
                }

                // Обновляем в БД те, что надо было обновить (через интерт, т.к. OnConflictStrategy.REPLACE)
                plantsToUpdate.forEach { plant ->
                    plantsRepository.insertPlant(plant)
                }
            }
        }
    }

    fun returnRemovedPlant(plant: Plant) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                plantsRepository.insertPlant(plant)
            }
        }
    }

    companion object {
        const val PROBABILITY_IS_NOT_WATERED = 0.2
        const val PROBABILITY_IS_LEAVES_FALLEN = 0.05
        const val PROBABILITY_IS_INSECTS_ATTACKED = 0.05
    }

}