package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.plantsbook.classes.Plant
import com.example.plantsbook.databinding.FragmentPlantInfoBinding
import com.google.android.material.snackbar.Snackbar

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

            // Добавляем растение
             binding.buttonAddPlant.setOnClickListener {
                 var plantListForAddButton = dataModel.plantListLiveData.value ?: mutableListOf<Plant>()
                 dataModel.plantListLiveData.value = plantListForAddButton // Подписываемся на изменения в списке растений (LiveData)

                 plantListForAddButton.add(_plant)

                 //Убираем ошибочно добавленное растение
                 Snackbar.make(requireView(), "Добавлено растение", Snackbar.LENGTH_SHORT)
                     .setAction("Отменить") {
                         plantListForAddButton.removeLast()
                     }.show()

             }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlantInfoFragment()
    }

}