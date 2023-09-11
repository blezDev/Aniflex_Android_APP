package com.blez.aniplex_clone.Presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.blez.aniplex_clone.Adapter.RecentAnimeAdapter
import com.blez.aniplex_clone.Adapter.homeAdapter.HomeAdapter
import com.blez.aniplex_clone.Presentation.recentanimerelease.RecentAnimeViewModel
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.FragmentHome2Binding
import com.blez.aniplex_clone.paging.LoaderAdapter
import com.blez.aniplex_clone.utils.SettingManager
import com.blez.aniplex_clone.utils.navigateSafely
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHome2Binding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter
    private lateinit var recentAdapter: RecentAnimeAdapter
    private lateinit var recentAnimeViewModel: RecentAnimeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHome2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        recentAnimeViewModel = ViewModelProvider(requireActivity())[RecentAnimeViewModel::class.java]
        recentAdapter =  RecentAnimeAdapter(requireContext())
        recentAnimeViewModel.getRecentPagingData()
        binding.recentRecyclerview.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        subscribeToUi()
        ObserveUIChanges()
        val settingManager = SettingManager(requireContext())
        viewModel.getRecommendation(settingManager.getSuggestion())
        recentAdapter.onItemClickText = { it ->

            findNavController().navigateSafely(
                R.id.action_homeFragment_to_detailsFragment,
                Bundle().apply { putString("animeId", it?.animeId) })
            Log.e("TAG", "onItemTextClicked on RecentFragment")
        }


    }
    private fun ObserveUIChanges() {

        lifecycleScope.launch(Dispatchers.Main) {
            recentAnimeViewModel.pagingData.collect { events ->
                when (events) {
                    is RecentAnimeViewModel.RecentEvent.Loading -> {
                        binding.progressView.isVisible = true
                    }

                    is RecentAnimeViewModel.RecentEvent.RecentPaging -> {
                        binding.progressView.isVisible = false
                        events.list.collect {
                            recentAdapter.submitData(lifecycle, it)
                            binding.recentRecyclerview.adapter =
                                recentAdapter.withLoadStateHeaderAndFooter(
                                    header = LoaderAdapter(),
                                    footer = LoaderAdapter()
                                )
                        }
                    }
                }

            }


        }

    }
    private fun subscribeToUi(){
        lifecycleScope.launch {
            viewModel.recommendationState.collect{events->
                when(events){
                    is HomeViewModel.RecommendationEvent.Failure ->{
                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                        Log.e("TAG",events.message.toString())
                        binding.progressView.isVisible = false
                    }
                    is HomeViewModel.RecommendationEvent.Success -> {
                        adapter = HomeAdapter(requireContext(),events.data)
                        binding.homeRecycler.adapter = adapter
                        binding.homeRecycler.layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                        binding.progressView.isVisible = false
                        binding.homeRecycler.isVisible = true
                        adapter.onItemClickText = {
                            findNavController().navigateSafely(
                                R.id.action_homeFragment_to_detailsFragment,
                                Bundle().apply { putString("animeId", it?.animeId) })
                        }
                    }
                    else-> Unit
                }

            }
        }
    }
}