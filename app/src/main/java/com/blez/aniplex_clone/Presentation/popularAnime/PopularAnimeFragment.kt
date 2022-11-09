package com.blez.aniplex_clone.Presentation.popularAnime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.Adapter.PopularAnimeAdapter
import com.blez.aniplex_clone.Presentation.recentanimerelease.RecentAnimeViewModel
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.PopularData
import retrofit2.Response


class PopularAnimeFragment : Fragment() {
    private lateinit var adapter : PopularAnimeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular_anime, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val PopularProgressBar = view.findViewById<ProgressBar>(R.id.PopularProgressBar)
        PopularProgressBar.visibility = View.VISIBLE
       val recentAnimeViewModel = ViewModelProvider(this)[RecentAnimeViewModel::class.java]
        val responseLiveData : LiveData<Response<PopularData>> = liveData {
            val response = recentAnimeViewModel.retService.getPopularAnime()
            emit(response)
        }

        responseLiveData.observe(viewLifecycleOwner, {
            PopularProgressBar.visibility = View.INVISIBLE
            val animeMovieList = it.body()
            if(animeMovieList!=null){
                adapter = PopularAnimeAdapter(requireContext(),animeMovieList)
                val movieView = view.findViewById<RecyclerView>(R.id.PopularRecyclerView)
                movieView.adapter = adapter
                movieView.layoutManager = GridLayoutManager(requireContext(),2)
                adapter.onItemClickText = {
                    val extras = PopularAnimeFragmentDirections.actionPopularAnimeFragmentToDetailsFragment(it?.animeId!!)
                    findNavController().navigate(extras)
                }

            }
        })
    }


}