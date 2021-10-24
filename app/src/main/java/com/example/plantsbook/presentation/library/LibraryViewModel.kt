package com.example.plantsbook.presentation.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantsbook.data.models.LibraryPlantItem
import com.example.plantsbook.data.models.PlantType
import com.example.plantsbook.data.repository.PlantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val plantsRepository: PlantsRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _items = MutableLiveData<List<LibraryPlantItem>>(emptyList())
    val items: LiveData<List<LibraryPlantItem>> = _items

    init {
        viewModelScope.launch {
            _isLoading.value = true
            delay(3000)
            _items.value = listOf(
                LibraryPlantItem(
                    name = "Test1",
                    description = "Test desc 1",
                    type = PlantType.DARLINGTONIA
                ),
                LibraryPlantItem(
                    name = "Test2",
                    description = "Test desc 2",
                    type = PlantType.ZHIRIANKA
                ),
                LibraryPlantItem(
                    name = "Test3",
                    description = "Test desc 3",
                    type = PlantType.PUZIRCHATKA
                ),
            )
            _isLoading.value = false
        }
    }

    fun insertPlant(item: LibraryPlantItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                plantsRepository.insertPlant(item.toPlant())
            }
        }
    }

}