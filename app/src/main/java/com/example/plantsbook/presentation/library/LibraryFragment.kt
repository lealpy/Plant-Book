package com.example.plantsbook.presentation.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plantsbook.R
import com.example.plantsbook.data.models.LibraryPlantItem
import com.example.plantsbook.databinding.FragmentLibraryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LibraryFragment : Fragment(R.layout.library_item) {

    lateinit var binding: FragmentLibraryBinding

    private val viewModel: LibraryViewModel by viewModels()

    private val libraryAdapter = LibraryAdapter(object : LibraryAdapter.OnItemClickListener {
        override fun onItemClick(item: LibraryPlantItem) {
            viewModel.insertPlant(item)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservers()
        initRecyclerView()
        initViews()
    }

    private fun initObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progress.visibility = if (isLoading) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        viewModel.items.observe(viewLifecycleOwner) { items ->
            libraryAdapter.submitList(items)
        }
    }

    private fun initRecyclerView() {
        binding.libraryItemsRecycler.layoutManager = LinearLayoutManager(context)
        binding.libraryItemsRecycler.adapter = libraryAdapter
    }

    private fun initViews() {

    }

    companion object {
        fun newInstance(): LibraryFragment {
            return LibraryFragment()
        }
    }

}