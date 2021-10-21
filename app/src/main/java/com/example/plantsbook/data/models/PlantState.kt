package com.example.plantsbook.data.models

data class PlantState(
    val isNotWatered: Boolean,
    var isLeavesFallen: Boolean,
    var isInsectsAttacked: Boolean,
) {
    fun isTriggered(): Boolean {
        return isNotWatered || isLeavesFallen || isInsectsAttacked
    }
    companion object {
        fun getIdle(): PlantState {
            return PlantState(
                isNotWatered = false,
                isLeavesFallen = false,
                isInsectsAttacked = false
            )
        }
    }
}
