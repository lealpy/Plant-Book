package com.example.plantsbook.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataModel
import com.example.plantsbook.R
import com.example.plantsbook.databinding.PlantItemBinding
import com.example.plantsbook.plantListDB

class PlantAdapter : RecyclerView.Adapter<PlantAdapter.PlantHolder>() {

    var plantListForRecyclerView = mutableListOf<Plant> ()

    class PlantHolder(item : View) : RecyclerView.ViewHolder(item) {
        val binding = PlantItemBinding.bind(item)

        fun toBind (plant : Plant) {
            binding.plantItemImage.setImageResource(plant.imageID)
            binding.plantItemTitle.text = plant.name
            if (plant.isTriggered()) {
                binding.indicatorItemImage.setImageResource(R.drawable.ic_baseline_fiber_manual_record_24)
            }
        }
    }

    //Функция надувает разметку с помощью inflate и создает экземпляр класса PlantHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plant_item, parent, false)
        return PlantHolder(view)
    }

    override fun onBindViewHolder(holder: PlantHolder, position: Int) {
        holder.toBind(plantListForRecyclerView[position])
    }

    override fun getItemCount(): Int {
        return plantListForRecyclerView.size
    }

    fun addRandomPlant() {
        var plantListRandomIndex = (0..plantListDB.lastIndex).random()
        val plant = Plant(plantListDB[plantListRandomIndex].name, plantListDB[plantListRandomIndex].imageID)
        plantListForRecyclerView.add(plant)
        notifyDataSetChanged() // Перерисовка при изменении данных
    }

    fun addSomePlants(list : List<Plant>){
        plantListForRecyclerView.addAll(list)
        notifyDataSetChanged() // Перерисовка при изменении данных
    }

    fun deletePlant(index : Int) {
        plantListForRecyclerView.removeAt(index)
        notifyDataSetChanged() // Перерисовка при изменении данных
    }

    fun returnDeletedPlant(_position: Int, _plant : Plant) {
        plantListForRecyclerView.add(_position, _plant)
        notifyDataSetChanged() // Обновляем recycler
    }

    fun returnDeletedPlant_v2(_plantList : MutableList<Plant>) {
        plantListForRecyclerView = _plantList
        notifyDataSetChanged() // Обновляем recycler
    }

}