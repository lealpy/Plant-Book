package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsbook.*
import com.example.plantsbook.classes.Plant
import com.example.plantsbook.classes.PlantAdapter
import com.example.plantsbook.databinding.FragmentGardenBinding
import com.google.android.material.snackbar.Snackbar


class GardenFragment : Fragment() {

    lateinit var binding : FragmentGardenBinding
    private val dataModel : DataModel by activityViewModels()
    private val plantAdapter = PlantAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentGardenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = GardenFragment()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(activity,3) // 3 - кол-во элементов в строке
        binding.recyclerView.adapter = plantAdapter

        //Восстанавливаем ранее добавленные растения в RecyclerView
        var plantLiveDataList = listOf<Plant>()
        dataModel.plantListLiveData.observe(activity as LifecycleOwner, { plantLiveDataList = it })
        plantAdapter.addSomePlants(plantLiveDataList)

        //Добавляем новое растение
        binding.buttonAddRandomPlant.setOnClickListener {
            var plantListRandomIndex = (0..plantList.lastIndex).random()
            val plant = Plant(plantList[plantListRandomIndex].name, plantList[plantListRandomIndex].imageID)
            plantAdapter.addPlant(plant)
            binding.recyclerView.smoothScrollToPosition(plantAdapter.getItemCount()-1) // Автоматическая прокрутка вниз
            dataModel.plantListLiveData.value = plantAdapter.plantListForRecyclerView // Сохраняем список отрисованных элементов в LiveData
        }
    }
}







