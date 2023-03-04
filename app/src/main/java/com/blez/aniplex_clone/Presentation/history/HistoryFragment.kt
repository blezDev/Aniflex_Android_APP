package com.blez.aniplex_clone.Presentation.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.blez.aniplex_clone.Adapter.HistoryAdapter
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private lateinit var binding : FragmentHistoryBinding
    private val historViewModel : HistorViewModel by viewModels()
    private lateinit var historyAdapter: HistoryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_history, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historViewModel.getListData()
        subscribeToUI()
    }

    private fun subscribeToUI(){
        lifecycleScope.launch(Dispatchers.Main) {
            historViewModel.data.collect{events->
                when(events){
                    is HistorViewModel.SealedEvent.Loading->{
                        binding.progressBar2.isVisible = true
                    }
                    is HistorViewModel.SealedEvent.ListData->{
                        binding.progressBar2.isVisible = false
                        historyAdapter = HistoryAdapter(requireContext(),events.data)
                        binding.HistoryRecycler.adapter = historyAdapter
                        binding.HistoryRecycler.layoutManager = LinearLayoutManager(requireContext())
                        Log.e("TAG",events.data.toString())
                    }
                    else -> Unit
                }

            }

        }
    }

}