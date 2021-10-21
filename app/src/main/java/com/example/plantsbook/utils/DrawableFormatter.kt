package com.example.plantsbook.utils

import com.example.plantsbook.R
import com.example.plantsbook.data.models.PlantType

fun formatTypeToImgResId(type: PlantType): Int {
    return when (type) {
        PlantType.zhirianka -> R.drawable.zhirianka0
        PlantType.muholovka -> R.drawable.muholovka1
        PlantType.nepentes -> R.drawable.nepentes2
        PlantType.rosianka -> R.drawable.rosyanka3
        PlantType.sarracenia -> R.drawable.sarracenia4
        PlantType.puzirchatka -> R.drawable.puzirchatka5
        PlantType.darlingtonia -> R.drawable.darlingtonia6
    }
}