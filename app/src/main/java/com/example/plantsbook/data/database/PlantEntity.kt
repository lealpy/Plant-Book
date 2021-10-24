package com.example.plantsbook.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plantsbook.data.models.Plant
import com.example.plantsbook.data.models.PlantState
import com.example.plantsbook.data.models.PlantType

@Entity
data class PlantEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val name: String,
    val plantType: PlantType,
    val description: String,
    @Embedded val plantState: PlantState
) {
    fun toPlant(): Plant {
        return Plant(
            id = id,
            name = name,
            type = plantType,
            description = description,
            state = plantState
        )
    }
}