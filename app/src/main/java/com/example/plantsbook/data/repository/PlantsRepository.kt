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
    val plantList: LiveData<MutableList<Plant>> = _plantList // База данных

    fun addPlant(plant: Plant) {
        val list = _plantList.value
        list?.let {__plantList ->
            __plantList.add(plant)
            _plantList.value = __plantList
        }
    }

    fun setPlantList(__plantList: MutableList<Plant>) {
        _plantList.value = __plantList.toMutableList()
    }

    fun getPlantByType(type: PlantType): Plant {
        return when (type) {
            PlantType.ZHIRIANKA -> Plant(
                name = resourceManager.getString(R.string.zhirianka),
                type = PlantType.ZHIRIANKA,
                description = resourceManager.getString(R.string.zhirianka_description),
                state = PlantState.getIdle()
            )
            PlantType.MUHOLOVKA -> Plant(
                name = resourceManager.getString(R.string.muholovka),
                type = PlantType.MUHOLOVKA,
                description = resourceManager.getString(R.string.muholovka_description),
                state = PlantState.getIdle()
            )
            PlantType.NEPENTES -> Plant(
                name = resourceManager.getString(R.string.nepentes),
                type = PlantType.NEPENTES,
                description = resourceManager.getString(R.string.nepentes_description),
                state = PlantState.getIdle()
            )
            PlantType.ROSIANKA -> Plant(
                name = resourceManager.getString(R.string.rosianka),
                type = PlantType.ROSIANKA,
                description = resourceManager.getString(R.string.rosianka_description),
                state = PlantState.getIdle()
            )
            PlantType.SARRACENIA -> Plant(
                name = resourceManager.getString(R.string.sarracenia),
                type = PlantType.SARRACENIA,
                description = resourceManager.getString(R.string.sarracenia_description),
                state = PlantState.getIdle()
            )
            PlantType.PUZIRCHATKA -> Plant(
                name = resourceManager.getString(R.string.puzirchatka),
                type = PlantType.PUZIRCHATKA,
                description = resourceManager.getString(R.string.puzirchatka_description),
                state = PlantState.getIdle()
            )
            PlantType.DARLINGTONIA -> Plant(
                name = resourceManager.getString(R.string.darlingtonia),
                type = PlantType.DARLINGTONIA,
                description = resourceManager.getString(R.string.darlingtonia_description),
                state = PlantState.getIdle()
            )
        }
    }

    fun getRandomPlant(): Plant {
        return getPlantByType(PlantType.values().random())
    }

}