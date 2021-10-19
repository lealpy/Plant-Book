package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsbook.classes.Plant
import com.example.plantsbook.classes.PlantAdapter
import com.example.plantsbook.databinding.FragmentGardenBinding
import com.google.android.material.snackbar.Snackbar


class GardenFragment : Fragment() {

    lateinit var binding : FragmentGardenBinding
    private val dataModel : DataModel by activityViewModels()
    private val plantAdapter = PlantAdapter()

    val SPAN_COUNT_PORTRAIT = 3 // Переместить в компаньон?
    val SPAN_COUNT_LANDSCAPE = 5 // Переместить в компаньон?

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

        binding.recyclerView.layoutManager = GridLayoutManager(activity, spanCount()) // spanCount - кол-во элементов в строке
        binding.recyclerView.adapter = plantAdapter

        //Восстанавливаем ранее добавленные растения
        var plantListRestore = listOf<Plant>()
        dataModel.plantListLiveData.observe(activity as LifecycleOwner, { plantListRestore = it })
        plantAdapter.addSomePlants(plantListRestore)

        // Подписываемся на актуальный список растений
        dataModel.plantListLiveData.value = plantAdapter.plantListForRecyclerView

        //Добавляем случайное растение
        binding.buttonAddRandomPlant.setOnClickListener {
            plantAdapter.addRandomPlant()
            binding.recyclerView.smoothScrollToPosition(plantAdapter.getItemCount()-1) // Автоматическая прокрутка вниз
        }

        //Удаляем растение по свайпу
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

                    val deletedPlantPosition = viewHolder.adapterPosition
                    val deletedPlant = plantAdapter.plantListForRecyclerView[deletedPlantPosition]
                    plantAdapter.deletePlant(deletedPlantPosition)

                    //Восстанавливаем ошибочно удаленное растение
                    Snackbar.make(requireView(), "Растение удалено", Snackbar.LENGTH_SHORT)
                        .setAction("Вернуть") {
                            plantAdapter.returnDeletedPlant(deletedPlantPosition, deletedPlant)
                            binding.recyclerView.smoothScrollToPosition(deletedPlantPosition) // Автоматическая прокрутка на восстановленную позицию
                        }.show()
                }

            }
        ).attachToRecyclerView(binding.recyclerView)
    }

    private fun spanCount() : Int {
        return when (getResources().getConfiguration().orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> SPAN_COUNT_LANDSCAPE
            Configuration.ORIENTATION_PORTRAIT -> SPAN_COUNT_PORTRAIT
            else -> 1
        }
    }

}