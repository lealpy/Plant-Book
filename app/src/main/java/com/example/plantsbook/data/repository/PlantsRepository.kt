package com.example.plantsbook.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.plantsbook.R
import com.example.plantsbook.ResourceManager
import com.example.plantsbook.data.database.PlantDao
import com.example.plantsbook.data.models.Plant
import com.example.plantsbook.data.models.PlantState
import com.example.plantsbook.data.models.PlantType
import javax.inject.Inject

class PlantsRepository @Inject constructor(
    private val resourceManager: ResourceManager,
    private val plantDao: PlantDao
) {

    fun getPlantsLD(): LiveData<List<Plant>> {
        return Transformations.map(plantDao.getPlantsLD()) { entities ->
            entities.map { entity ->
                entity.toPlant()
            }
        }
    }

    suspend fun getPlants(): List<Plant> {
        return plantDao.getPlants().map { plantEntity ->
            plantEntity.toPlant()
        }
    }

    suspend fun getPlant(id: Long): Plant {
        return plantDao.get(id).toPlant()
    }

    suspend fun insertPlant(plant: Plant) {
        plantDao.insert(plant.toEntity())
    }

    suspend fun insertRandomPlant() {
        val randomPlant = getRandomPlant()
        insertPlant(randomPlant)
    }

    suspend fun deletePlant(plant: Plant) {
        plantDao.delete(plant.toEntity())
    }

    private fun getPlantByType(type: PlantType): Plant {
        return when (type) {
            PlantType.ZHIRIANKA -> Plant(
                id = null,
                name = resourceManager.getString(R.string.zhirianka),
                type = PlantType.ZHIRIANKA,
                description = resourceManager.getString(R.string.zhirianka_description),
                state = PlantState.getIdle()
            )
            PlantType.MUHOLOVKA -> Plant(
                id = null,
                name = resourceManager.getString(R.string.muholovka),
                type = PlantType.MUHOLOVKA,
                description = resourceManager.getString(R.string.muholovka_description),
                state = PlantState.getIdle()
            )
            PlantType.NEPENTES -> Plant(
                id = null,
                name = resourceManager.getString(R.string.nepentes),
                type = PlantType.NEPENTES,
                description = resourceManager.getString(R.string.nepentes_description),
                state = PlantState.getIdle()
            )
            PlantType.ROSIANKA -> Plant(
                id = null,
                name = resourceManager.getString(R.string.rosianka),
                type = PlantType.ROSIANKA,
                description = resourceManager.getString(R.string.rosianka_description),
                state = PlantState.getIdle()
            )
            PlantType.SARRACENIA -> Plant(
                id = null,
                name = resourceManager.getString(R.string.sarracenia),
                type = PlantType.SARRACENIA,
                description = resourceManager.getString(R.string.sarracenia_description),
                state = PlantState.getIdle()
            )
            PlantType.PUZIRCHATKA -> Plant(
                id = null,
                name = resourceManager.getString(R.string.puzirchatka),
                type = PlantType.PUZIRCHATKA,
                description = resourceManager.getString(R.string.puzirchatka_description),
                state = PlantState.getIdle()
            )
            PlantType.DARLINGTONIA -> Plant(
                id = null,
                name = resourceManager.getString(R.string.darlingtonia),
                type = PlantType.DARLINGTONIA,
                description = resourceManager.getString(R.string.darlingtonia_description),
                state = PlantState.getIdle()
            )
        }
    }

    private fun getRandomPlant(): Plant {
        return getPlantByType(PlantType.values().random())
    }

}