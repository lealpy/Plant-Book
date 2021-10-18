package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import com.example.plantsbook.*
import com.example.plantsbook.classes.Plant
import com.example.plantsbook.classes.PlantAdapter
import com.example.plantsbook.databinding.FragmentGardenBinding


class GardenFragment : Fragment() {

    lateinit var binding : FragmentGardenBinding
    private val dataModel : DataModel by activityViewModels()
    private val adapter = PlantAdapter()

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
        binding.recyclerView.adapter = adapter

        //Восстанавливаем ранее добавленные растения в RecyclerView
        var plantLiveDataList = listOf<Plant>()
        dataModel.plantListLiveData.observe(activity as LifecycleOwner, { plantLiveDataList = it })
        adapter.addSomePlants(plantLiveDataList)

        //Добавляем новое растение
        binding.buttonAddRandomPlant.setOnClickListener {
            var plantListRandomIndex = (0..plantList.lastIndex).random()
            val plant = Plant(plantList[plantListRandomIndex].name, plantList[plantListRandomIndex].imageID)
            adapter.addPlant(plant)
            plantListRandomIndex ++
            binding.recyclerView.smoothScrollToPosition(adapter.getItemCount()-1) // Автоматическая прокрутка вниз
            dataModel.plantListLiveData.value = adapter.plantListForRecyclerView // Сохраняем список отрисованных элементов в LiveData
        }
    }
}







