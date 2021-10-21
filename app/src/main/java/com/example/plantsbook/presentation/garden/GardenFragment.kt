package com.example.plantsbook.presentation.garden

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.plantsbook.databinding.FragmentGardenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GardenFragment : Fragment() {

    lateinit var binding: FragmentGardenBinding

    private val viewModel: GardenViewModel by viewModels()

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
        initViews()
        initObservers()
    }

    private fun initRecyclerView() {
        // spanCount - кол-во элементов в строке
        binding.recyclerView.layoutManager = GridLayoutManager(context, getSpanCount())
        binding.recyclerView.adapter = plantAdapter

//        //Удаляем растение по свайпу
//        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
//            0,
//            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//        ) {
//
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//
//                val deletedPlantPosition = viewHolder.adapterPosition
//                val deletedPlant = plantAdapter.plantList[deletedPlantPosition]
//                plantAdapter.deletePlant(deletedPlantPosition)
//
//                //Восстанавливаем ошибочно удаленное растение
//                Snackbar.make(requireView(), "Растение удалено", Snackbar.LENGTH_SHORT)
//                    .setAction("Вернуть") {
//                        plantAdapter.returnDeletedPlant(deletedPlantPosition, deletedPlant)
//                        binding.recyclerView.smoothScrollToPosition(deletedPlantPosition) // Автоматическая прокрутка на восстановленную позицию
//                    }.show()
//
//                mainViewModel.plantListLiveData.value =
//                    plantAdapter.plantList // Подписываемся на актуальный список растений
//
//            }
//        }
//        ).attachToRecyclerView(binding.recyclerView)
    }

    private fun initViews() {
        //Добавляем случайное растение
        binding.buttonAddRandomPlant.setOnClickListener {
            viewModel.addRandomPlant()
        }

        binding.buttonNextDay.setOnClickListener {
            viewModel.nextDay()
        }
    }

    private fun initObservers() {
        viewModel.plantList.observe(viewLifecycleOwner) { plants ->
            plantAdapter.setData(plants)
        }
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