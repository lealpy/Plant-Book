package com.example.plantsbook.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.plantsbook.R
import com.example.plantsbook.ResourceManager
import com.example.plantsbook.data.models.Plant
import com.example.plantsbook.data.models.PlantState
import com.example.plantsbook.data.models.PlantType
import javax.inject.Inject

class PlantsRepository @Inject constructor(
    private val resourceManager: ResourceManager
) {

    private val _plantList = MutableLiveData<MutableList<Plant>>(mutableListOf())
    val plantList: LiveData<MutableList<Plant>> = _plantList

    fun addPlant(plant: Plant) {
        val list = _plantList.value
        list?.let {
            it.add(plant)
            _plantList.value = it
        }
    }

    fun setPlants(plants: List<Plant>) {
        _plantList.value = plants.toMutableList()
    }

    fun getPlantByType(type: PlantType): Plant {
        return when (type) {
            PlantType.zhirianka -> Plant(
                name = resourceManager.getString(R.string.zhirianka),
                type = PlantType.zhirianka,
                description = resourceManager.getString(R.string.zhirianka_description),
                state = PlantState.getIdle()
            )
            PlantType.muholovka -> Plant(
                name = resourceManager.getString(R.string.muholovka),
                type = PlantType.muholovka,
                description = resourceManager.getString(R.string.zhirianka_description),
                state = PlantState.getIdle()
            )
            PlantType.nepentes -> Plant(
                name = resourceManager.getString(R.string.nepentes),
                type = PlantType.nepentes,
                description = resourceManager.getString(R.string.zhirianka_description),
                state = PlantState.getIdle()
            )
            PlantType.rosianka -> Plant(
                name = resourceManager.getString(R.string.rosianka),
                type = PlantType.rosianka,
                description = resourceManager.getString(R.string.zhirianka_description),
                state = PlantState.getIdle()
            )
            PlantType.sarracenia -> Plant(
                name = resourceManager.getString(R.string.sarracenia),
                type = PlantType.sarracenia,
                description = resourceManager.getString(R.string.zhirianka_description),
                state = PlantState.getIdle()
            )
            PlantType.puzirchatka -> Plant(
                name = resourceManager.getString(R.string.puzirchatka),
                type = PlantType.puzirchatka,
                description = resourceManager.getString(R.string.zhirianka_description),
                state = PlantState.getIdle()
            )
            PlantType.darlingtonia -> Plant(
                name = resourceManager.getString(R.string.darlingtonia),
                type = PlantType.darlingtonia,
                description = resourceManager.getString(R.string.zhirianka_description),
                state = PlantState.getIdle()
            )
        }
    }

    fun getRandomPlant(): Plant {
        return getPlantByType(PlantType.values().random())
    }

}