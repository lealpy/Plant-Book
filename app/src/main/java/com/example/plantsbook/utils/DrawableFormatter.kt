package com.example.plantsbook.utils

import com.example.plantsbook.R
import com.example.plantsbook.data.models.PlantType

fun formatTypeToImgResId(type: PlantType): Int {
    return when (type) {
        PlantType.ZHIRIANKA -> R.drawable.zhirianka0
        PlantType.MUHOLOVKA -> R.drawable.muholovka1
        PlantType.NEPENTES -> R.drawable.nepentes2
        PlantType.ROSIANKA -> R.drawable.rosyanka3
        PlantType.SARRACENIA -> R.drawable.sarracenia4
        PlantType.PUZIRCHATKA -> R.drawable.puzirchatka5
        PlantType.DARLINGTONIA -> R.drawable.darlingtonia6
    }
}