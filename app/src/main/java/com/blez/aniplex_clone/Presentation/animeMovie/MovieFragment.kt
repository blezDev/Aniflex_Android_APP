package com.blez.aniplex_clone.Presentation.animeMovie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.Adapter.AnimeMoviesAdapter
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.`interface`.AnimeInterface
import com.blez.aniplex_clone.data.MovieData
import com.blez.aniplex_clone.network.RetrofitInstance
import retrofit2.Response

class MovieFragment : Fragment() {
    private lateinit var adapter: AnimeMoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieProgressBar = view.findViewById<ProgressBar>(R.id.movieProgressBar)
        movieProgressBar.visibility = View.VISIBLE
        val retService = RetrofitInstance.getRetrofitInstance()
            .create(AnimeInterface::class.java)
        val responseLiveData : LiveData<Response<MovieData>> = liveData {
            val response = retService.getMoviesList()
            emit(response)
        }

        responseLiveData.observe(viewLifecycleOwner, Observer {
            movieProgressBar.visibility = View.INVISIBLE
            val animeMovieList = it.body()
            if(animeMovieList!=null){
                adapter = AnimeMoviesAdapter(requireContext(),animeMovieList)
                val movieView = view.findViewById<RecyclerView>(R.id.MovieRecyclerView)
                movieView.adapter = adapter
                movieView.layoutManager = GridLayoutManager(requireContext(),2)

            }
        })
    }

}