package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsbook.activities.StartActivity.Companion.PROBABILITY_IS_INSECTS_ATTACKED
import com.example.plantsbook.activities.StartActivity.Companion.PROBABILITY_IS_LEAVES_FALLEN
import com.example.plantsbook.activities.StartActivity.Companion.PROBABILITY_IS_NOT_WATERED
import com.example.plantsbook.activities.StartActivity.Companion.SPAN_COUNT_LANDSCAPE
import com.example.plantsbook.activities.StartActivity.Companion.SPAN_COUNT_PORTRAIT
import com.example.plantsbook.classes.Plant
import com.example.plantsbook.classes.PlantAdapter
import com.example.plantsbook.classes.RecyclerItemClickListener
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

        //Восстанавливаем ранее добавленные растения
        var plantListRestore = listOf<Plant>()
        dataModel.plantListLiveData.observe(activity as LifecycleOwner, { plantListRestore = it })
        plantAdapter.addSomePlants(plantListRestore)

        bindRecyclerView()

        //Добавляем случайное растение
        binding.buttonAddRandomPlant.setOnClickListener {
            plantAdapter.addRandomPlant()
            binding.recyclerView.smoothScrollToPosition(plantAdapter.getItemCount()-1) // Автоматическая прокрутка вниз
            dataModel.plantListLiveData.value = plantAdapter.plantListForRecyclerView // Подписываемся на актуальный список растений
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

                    dataModel.plantListLiveData.value = plantAdapter.plantListForRecyclerView // Подписываемся на актуальный список растений

                }
            }
        ).attachToRecyclerView(binding.recyclerView)

        binding.buttonNextDay.setOnClickListener {
            nextDay(plantAdapter.plantListForRecyclerView)
        }

        binding.tittleGarden.setOnClickListener {
            Toast.makeText(activity, "clicked", Toast.LENGTH_LONG).show() }
       }

    private fun spanCount() : Int {
        return when (getResources().getConfiguration().orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> SPAN_COUNT_LANDSCAPE
            Configuration.ORIENTATION_PORTRAIT -> SPAN_COUNT_PORTRAIT
            else -> 1
        }
    }

    private fun bindRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(activity, spanCount()) // spanCount - кол-во элементов в строке
        binding.recyclerView.adapter = plantAdapter
    }

    private fun nextDay(_plantList : MutableList<Plant>) {

        _plantList.removeAll{ it.isTriggered() }

        bindRecyclerView()

        _plantList.forEach {
            it.isNotWatered = (Math.random() < PROBABILITY_IS_NOT_WATERED)
            it.isLeavesFallen = (Math.random() < PROBABILITY_IS_LEAVES_FALLEN)
            it.isInsectsAttacked = (Math.random() < PROBABILITY_IS_INSECTS_ATTACKED)
        }

    }

    inline fun RecyclerView.setOnItemClickListener(crossinline listener: (position: Int) -> Unit) {
        addOnItemTouchListener(RecyclerItemClickListener(this,
            object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    listener(position)
                }
            }))
    }

}

/*
    inline fun RecyclerView.setOnItemClickListener(crossinline listener: (position: Int) -> Unit) {
        addOnItemTouchListener(RecyclerItemClickListener(this,
            object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    listener(position)
                }
            }))
    }


        var index = 0
        for(el in _plantList) {
            if (el.isTriggered() && plantAdapter.plantListForRecyclerView[index] != null) {
                plantAdapter.deletePlant(index)
            }
            else index ++
        }

for((elIndex, el) in _plantList.withIndex()) {
    if (el.isTriggered()) {
        plantAdapter.deletePlant(elIndex)
    }
}


        for(i in 0.._plantList.lastIndex) {
            if (_plantList[i].isTriggered()) {
                plantAdapter.deletePlant(i)
            }
        }
 */