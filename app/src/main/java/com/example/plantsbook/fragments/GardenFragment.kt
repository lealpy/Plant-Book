package com.example.myapplication

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

        //Восстанавливаем ранее добавленные растения
        var plantListRestore = listOf<Plant>()
        dataModel.plantListLiveData.observe(activity as LifecycleOwner, { plantListRestore = it })
        plantAdapter.addSomePlants(plantListRestore)

        //Добавляем случайное растение
        binding.buttonAddRandomPlant.setOnClickListener {
            plantAdapter.addRandomPlant()
            binding.recyclerView.smoothScrollToPosition(plantAdapter.getItemCount()-1) // Автоматическая прокрутка вниз
            dataModel.plantListLiveData.value = plantAdapter.plantListForRecyclerView // Сохраняем список отрисованных элементов в LiveData
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

                    //Восстанавливаем удаленное растение
                    Snackbar.make(requireView(), "Растение удалено", Snackbar.LENGTH_SHORT)
                        .setAction("Вернуть") {
                            plantAdapter.returnDeletedPlant(deletedPlantPosition, deletedPlant)
                        }.show()

                }

                }
            ).attachToRecyclerView(binding.recyclerView)
    }
}







/*
override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

val plantListBeforeDelete = plantAdapter.plantListForRecyclerView // - восстановление не работает
//var plantListBeforeDelete = dataModel.plantListLiveData.value ?: mutableListOf<Plant>() // - не работает
//var plantListBeforeDelete = mutableListOf(zhirianka, sarracenia) // - а если создать новый список - работает

plantAdapter.deletePlant(viewHolder.adapterPosition)

Snackbar.make(requireView(), "Task deleted", Snackbar.LENGTH_SHORT)
    .setAction("Вернуть растение") {
         plantAdapter.returnDeletedPlant_v2(plantListBeforeDelete)
    }.show()

}
*/