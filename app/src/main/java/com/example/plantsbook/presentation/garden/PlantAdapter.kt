package com.example.plantsbook.presentation.garden

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
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

            binding.imgIsNotWatered.setImageResource(R.drawable.img_is_not_watered)
            if (!plant.state.isNotWatered) {
                binding.imgIsNotWatered.visibility = View.INVISIBLE
            }

            binding.imgLeavesFallen.setImageResource(R.drawable.img_leaves_fallen)
            if (!plant.state.isLeavesFallen) {
                binding.imgLeavesFallen.visibility = View.INVISIBLE
            }

            binding.imgInsectsAttacked.setImageResource(R.drawable.img_insects_attacked)
            if (!plant.state.isInsectsAttacked) {
                binding.imgInsectsAttacked.visibility = View.INVISIBLE
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