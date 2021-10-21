package com.example.plantsbook.data.models

data class Plant(
    val name: String,
    val type: PlantType,
    val description: String,
    var state: PlantState
)
