package com.example.plantsbook.presentation.plant_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.plantsbook.data.models.PlantType
import com.example.plantsbook.databinding.FragmentPlantInfoBinding
import com.example.plantsbook.utils.formatTypeToImgResId
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantInfoFragment : Fragment() {

    private val viewModel: PlantInfoViewModel by viewModels()

    lateinit var binding: FragmentPlantInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPlantInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getInt(PLANT_TYPE_ORDINAL)?.let { typeOrdinal ->
            viewModel.fetchPlantInfo(typeOrdinal)
        } ?: Toast.makeText(context, "Сорян, но что-то пошло не так", Toast.LENGTH_SHORT).show()


        viewModel.plantInfo.observe(viewLifecycleOwner, { plant ->
            binding.tittlePlantInfo.text = plant.name
            binding.imagePlantInfo.setImageResource(formatTypeToImgResId(plant.type))
            binding.descriptionPlantInfo.text = plant.description
        })

        // Добавляем растение
        binding.buttonAddPlant.setOnClickListener {
            viewModel.addPlant()
        }
    }

    companion object {
        private const val PLANT_TYPE_ORDINAL = "PLANT_TYPE_ORDINAL"

        @JvmStatic
        fun newInstance(plantType: PlantType): PlantInfoFragment {
            return PlantInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(PLANT_TYPE_ORDINAL, plantType.ordinal)
                }
            }
        }
    }

}