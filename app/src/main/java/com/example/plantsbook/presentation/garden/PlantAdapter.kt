package com.example.plantsbook.presentation.garden

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsbook.R
import com.example.plantsbook.data.models.Plant
import com.example.plantsbook.databinding.PlantItemBinding
import com.example.plantsbook.utils.formatTypeToImgResId

class PlantAdapter : RecyclerView.Adapter<PlantAdapter.PlantHolder>() {

    private var plants = listOf<Plant>()

    class PlantHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = PlantItemBinding.bind(item)

        fun toBind(plant: Plant) {
            binding.plantItemImage.setImageResource(formatTypeToImgResId(plant.type))
            binding.plantItemTitle.text = plant.name
            if (plant.state.isTriggered()) {
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
        holder.toBind(plants[position])
    }

    override fun getItemCount(): Int {
        return plants.size
    }

    fun setData(plants: List<Plant>) {
        this.plants = plants
        notifyDataSetChanged()
    }

}