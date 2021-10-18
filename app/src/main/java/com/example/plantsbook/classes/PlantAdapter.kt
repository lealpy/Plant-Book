package com.example.plantsbook.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsbook.R
import com.example.plantsbook.databinding.PlantItemBinding

class PlantAdapter : RecyclerView.Adapter<PlantAdapter.PlantHolder>() {

    val plantListForRecyclerView = mutableListOf<Plant> ()

    class PlantHolder(item : View) : RecyclerView.ViewHolder(item) {
        val binding = PlantItemBinding.bind(item)
        //with(binding) - ассоциирует все команды с binding. Используем чтобы не писать в каждой строчке функции "binding"
        fun toBind (plant : Plant) = with(binding) {
            plantImage.setImageResource(plant.imageID)
            tvTitle.text = plant.name
        }
    }

    //Берет разметку, надувает с помощью inflate и создает экземпляр класса PlantHolder
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

    fun addPlant(plant : Plant) {
        plantListForRecyclerView.add(plant)
        notifyDataSetChanged() // Перерисовка при изменении данных
    }

    fun addSomePlants(list : List<Plant>){
        plantListForRecyclerView.addAll(list)
        notifyDataSetChanged()
    }

}