package com.example.plantsbook.presentation.garden

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsbook.R
import com.example.plantsbook.data.models.Plant
import com.example.plantsbook.databinding.PlantItemBinding
import com.example.plantsbook.utils.formatTypeToImgResId

class GardenAdapter(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<Plant, GardenAdapter.PlantHolder>(DiffCallback()) {

    inner class PlantHolder(
        private val binding: PlantItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                binding.root.setOnClickListener {
                    val position = layoutPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val plant = getItem(position)
                        onItemClickListener.onItemClick(plant)
                    }
                }
            }
        }

        fun bind(plant: Plant) {
            binding.plantItemImage.setImageResource(formatTypeToImgResId(plant.type))
            binding.plantItemTitle.text = plant.name

            binding.imgIsNotWatered.setImageResource(R.drawable.img_is_not_watered)
            binding.imgIsNotWatered.visibility = if (plant.state.isNotWatered) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }

            binding.imgLeavesFallen.setImageResource(R.drawable.img_leaves_fallen)
            binding.imgLeavesFallen.visibility = if (plant.state.isLeavesFallen) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }

            binding.imgInsectsAttacked.setImageResource(R.drawable.img_insects_attacked)
            binding.imgInsectsAttacked.visibility = if (plant.state.isInsectsAttacked) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantHolder {
        val binding = PlantItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlantHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    interface OnItemClickListener {
        fun onItemClick(plant: Plant)
    }

    class DiffCallback : DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant) =
            oldItem == newItem
    }

    fun getPlantItem(position: Int): Plant {
        return getItem(position)
    }

}