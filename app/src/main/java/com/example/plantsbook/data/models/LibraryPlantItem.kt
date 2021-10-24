package com.example.plantsbook.data.models

data class LibraryPlantItem(
    val name: String,
    val description: String,
    val type: PlantType
) {
    fun toPlant(): Plant {
        return Plant(
            id = null,
            name = name,
            description = description,
            type = type,
            state = PlantState.getIdle()
        )
    }
}
