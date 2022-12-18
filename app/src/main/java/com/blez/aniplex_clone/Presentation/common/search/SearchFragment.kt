package com.blez.aniplex_clone.Presentation.common.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.blez.aniplex_clone.Adapter.SearchAdapter
import com.blez.aniplex_clone.Presentation.recentanimerelease.RecentAnimeViewModel
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.SearchAnime
import com.blez.aniplex_clone.databinding.FragmentSearchBinding
import retrofit2.Response


class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding
    private lateinit var adapter : SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rotate_animation(binding.RecentProgressBar)
        val recentAnimeViewModel = ViewModelProvider(this)[RecentAnimeViewModel::class.java]

        val text = arguments?.getString("animeQuery")
       val response : LiveData<Response<SearchAnime>> = liveData {
         emit(recentAnimeViewModel.retService.getAnimeSearch(text.toString()))
       }
        response.observe(viewLifecycleOwner, Observer {
            val animeList = it.body()
            if(animeList!=null){
                binding.progressView.visibility = View.INVISIBLE
                adapter = SearchAdapter(animeList,requireContext())
                binding.searchRecyclerView.adapter = adapter

                binding.searchRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
                adapter.onItemClick = {
                    val extra = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(it.animeId)
                    findNavController().navigate(extra)
                }

            }

        })



        super.onViewCreated(view, savedInstanceState)
    }
    fun rotate_animation( ImageView : ImageView?){
        val rotate = AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_clockwise)
        ImageView?.startAnimation(rotate)
    }


}