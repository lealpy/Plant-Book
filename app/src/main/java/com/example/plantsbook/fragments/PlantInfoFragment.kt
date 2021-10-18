package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.plantsbook.R
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

        dataModel.messageForPlantInfoFragment.observe(activity as LifecycleOwner, {
                 binding.tittlePlantInfo.text = it.name
                 binding.imagePlantInfo.setImageResource(it.imageID)
                 binding.descriptionPlantInfo.text = it.description
        })

    }

    companion object {
        @JvmStatic
        fun newInstance() = PlantInfoFragment()

    }
}