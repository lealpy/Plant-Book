package com.example.plantsbook.data.models

import com.example.plantsbook.data.database.PlantEntity

data class Plant(
    val id: Long?,
    val name: String,
    val type: PlantType,
    val description: String,
    var state: PlantState
) {
    fun toEntity(): PlantEntity {
        return PlantEntity(
            id = id,
            name = name,
            plantType = type,
            description = description,
            plantState = state
        )
    }
}
