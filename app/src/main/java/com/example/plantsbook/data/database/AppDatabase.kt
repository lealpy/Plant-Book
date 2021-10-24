package com.example.plantsbook.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PlantEntity::class], version = 1, exportSchema = false)
@TypeConverters(PlantConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun plantDao(): PlantDao

}