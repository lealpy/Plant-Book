package com.example.plantsbook.classes

data class Plant(val name : String, val imageID : Int, val description: String = "") {
    var isNotWatered = false
    var isLeavesFallen = false
    var isInsectsAttacked = false

    fun isTriggered () : Boolean {
        return isNotWatered || isLeavesFallen || isInsectsAttacked
    }

}
