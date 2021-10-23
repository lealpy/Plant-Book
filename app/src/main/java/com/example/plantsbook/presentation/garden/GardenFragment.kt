package com.example.plantsbook.presentation.garden

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsbook.data.models.Plant
import com.example.plantsbook.databinding.FragmentGardenBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GardenFragment : Fragment() {

    lateinit var binding: FragmentGardenBinding

    private val viewModel: GardenViewModel by viewModels()

    val plantAdapter = PlantAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentGardenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservers()
        initRecyclerView()
        initViews()
    }

    private fun initRecyclerView() {
        // spanCount - кол-во элементов в строке
        binding.recyclerView.layoutManager = GridLayoutManager(context, getSpanCount())
        binding.recyclerView.adapter = plantAdapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val removedPlantPosition = viewHolder.adapterPosition
                //Удаляем растение по свайпу
                viewModel.removePlant(removedPlantPosition)

                //Восстанавливаем ошибочно удаленное растение
                Snackbar.make(requireView(), "Растение удалено", Snackbar.LENGTH_SHORT)
                    .setAction("Вернуть") {
                        viewModel.returnRemovedPlant(removedPlantPosition)
                        // Автоматическая прокрутка на восстановленную позицию
                        binding.recyclerView.smoothScrollToPosition(removedPlantPosition)
                    }.show()
            }
        }
        ).attachToRecyclerView(binding.recyclerView)

    }

    private fun initObservers() {
        viewModel.plantList.observe(viewLifecycleOwner) { __plantList ->
            plantAdapter.setData(__plantList)
        }
    }

    private fun initViews() {
        //Добавляем случайное растение
        binding.buttonAddRandomPlant.setOnClickListener {
            viewModel.addRandomPlant()
            binding.recyclerView.adapter = plantAdapter // Перестраиваем recycler для обновления индикаторов
        }

        binding.buttonNextDay.setOnClickListener {
            nextDay()
            binding.recyclerView.adapter = plantAdapter // Перестраиваем recycler для обновления индикаторов
        }

        binding.buttonRefresh?.setOnClickListener {
            binding.recyclerView.adapter = plantAdapter // Перестраиваем recycler для обновления индикаторов
        }
    }

    private fun nextDay() {
        viewModel.nextDay()
        binding.recyclerView.adapter = plantAdapter // Перестраиваем recycler для обновления индикаторов
    }

    private fun getSpanCount(): Int {
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> SPAN_COUNT_LANDSCAPE
            Configuration.ORIENTATION_PORTRAIT -> SPAN_COUNT_PORTRAIT
            else -> 1
        }
    }

    companion object {

        const val SPAN_COUNT_PORTRAIT = 3
        const val SPAN_COUNT_LANDSCAPE = 5

        @JvmStatic
        fun newInstance() = GardenFragment()
    }

}