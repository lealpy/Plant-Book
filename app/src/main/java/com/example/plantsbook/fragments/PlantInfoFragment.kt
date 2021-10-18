package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.plantsbook.classes.Plant
import com.example.plantsbook.databinding.FragmentPlantInfoBinding

class PlantInfoFragment : Fragment() {

    private val dataModel : DataModel by activityViewModels()
    lateinit var binding : FragmentPlantInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPlantInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dataModel.plantLiveData.observe(activity as LifecycleOwner, { _plant ->
                 binding.tittlePlantInfo.text = _plant.name
                 binding.imagePlantInfo.setImageResource(_plant.imageID)
                 binding.descriptionPlantInfo.text = _plant.description
                 binding.buttonAddPlant.setOnClickListener {
                     var plantListForAddButton = dataModel.plantListLiveData.value ?: mutableListOf<Plant>()
                     plantListForAddButton.add(_plant)
                     dataModel.plantListLiveData.value = plantListForAddButton // Сохраняем список добавленных элементов в LiveData
                     Toast.makeText(getActivity(), "Добавлено растение ${_plant.name}", Toast.LENGTH_SHORT).show()
                 }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlantInfoFragment()
    }

}