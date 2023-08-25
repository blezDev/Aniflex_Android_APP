package com.blez.aniplex_clone.Presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.blez.aniplex_clone.Adapter.homeAdapter.HomeAdapter
import com.blez.aniplex_clone.databinding.FragmentHome2Binding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHome2Binding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter

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
        subscribeToUi()

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
                        binding.homeRecycler.layoutManager= GridLayoutManager(requireContext(),2)
                        binding.progressView.isVisible = false
                        binding.homeRecycler.isVisible = true
                    }
                    else-> Unit
                }

            }
        }
    }
}