package com.example.plantsbook.data.database

import androidx.room.TypeConverter
import com.example.plantsbook.data.models.PlantType

class PlantConverter {

    @TypeConverter
    fun toPlantType(value: String) = enumValueOf<PlantType>(value)

    @TypeConverter
    fun fromPlantType(value: PlantType) = value.name

}

