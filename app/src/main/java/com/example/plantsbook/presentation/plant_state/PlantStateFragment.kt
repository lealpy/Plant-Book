package com.example.plantsbook.presentation.plant_state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.plantsbook.databinding.FragmentPlantStateBinding
import com.example.plantsbook.utils.formatTypeToImgResId
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantStateFragment : Fragment() {

    private val viewModel: PlantStateViewModel by viewModels()

    lateinit var binding: FragmentPlantStateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPlantStateBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getLong(PLANT_ID)?.let { id ->
            viewModel.fetchPlant(id)
        } ?: Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()

        viewModel.plant.observe(viewLifecycleOwner, { plant ->
            if (plant != null) {
                binding.tittlePlantInfo.text = plant.name
                binding.imagePlantInfo.setImageResource(formatTypeToImgResId(plant.type))
                binding.descriptionPlantInfo.text = plant.description
            }
        })
    }

    companion object {
        private const val PLANT_ID = "PLANT_ID"

        @JvmStatic
        fun newInstance(id: Long): PlantStateFragment {
            return PlantStateFragment().apply {
                arguments = Bundle().apply {
                    putLong(PLANT_ID, id)
                }
            }
        }
    }

}