package com.example.plantsbook.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlantDao {

    @Query("SELECT * FROM plantentity")
    fun getPlantsLD(): LiveData<List<PlantEntity>>

    @Query("SELECT * FROM plantentity")
    suspend fun getPlants(): List<PlantEntity>

    @Query("SELECT * FROM plantentity WHERE id = :id")
    suspend fun get(id: Long): PlantEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plantEntity: PlantEntity)

    @Delete
    suspend fun delete(plantEntity: PlantEntity)

}