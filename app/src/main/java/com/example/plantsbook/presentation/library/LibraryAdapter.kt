package com.example.plantsbook.presentation.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsbook.data.models.LibraryPlantItem
import com.example.plantsbook.databinding.LibraryItemBinding
import com.example.plantsbook.utils.formatTypeToImgResId

class LibraryAdapter(
    private val listener: OnItemClickListener,
) : ListAdapter<LibraryPlantItem, LibraryAdapter.ItemHolder>(DiffCallback()) {

    inner class ItemHolder(
        private val binding: LibraryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                plantAddButton.setOnClickListener {
                    val position = layoutPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        listener.onItemClick(task)
                    }
                }
            }
        }

        fun bind(item: LibraryPlantItem) {
            binding.plantName.text = item.name
            binding.plantDescription.text = item.description
            binding.plantImage.setImageResource(formatTypeToImgResId(item.type))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = LibraryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    interface OnItemClickListener {
        fun onItemClick(item: LibraryPlantItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<LibraryPlantItem>() {
        override fun areItemsTheSame(oldItem: LibraryPlantItem, newItem: LibraryPlantItem) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: LibraryPlantItem, newItem: LibraryPlantItem) =
            oldItem == newItem
    }

}