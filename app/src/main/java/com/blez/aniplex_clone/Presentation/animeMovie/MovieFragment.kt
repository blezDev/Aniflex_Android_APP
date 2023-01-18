package com.blez.aniplex_clone.Presentation.animeMovie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.blez.aniplex_clone.Adapter.AnimeMoviesAdapter
import com.blez.aniplex_clone.Presentation.recentanimerelease.RecentAnimeViewModel
import com.blez.aniplex_clone.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        val movieProgressView = view.findViewById<ConstraintLayout>(R.id.progressView)
        val movieProgressBar = view.findViewById<ImageView>(R.id.detailProgressBar)

        rotate_animation(movieProgressBar)
        val recentAnimeViewModel = ViewModelProvider(this).get(RecentAnimeViewModel::class.java)

         /*   val it = recentAnimeViewModel
            movieProgressView.visibility = View.INVISIBLE
            stop_animation(movieProgressBar)
            val animeMovieList = it.body()
            if(animeMovieList!=null){
                adapter = AnimeMoviesAdapter(requireContext(),animeMovieList)
                val movieView = view.findViewById<RecyclerView>(R.id.MovieRecyclerView)
                movieView.adapter = adapter
                movieView.layoutManager = GridLayoutManager(requireContext(),2)

            }*/

    }
    fun rotate_animation( ImageView : ImageView?){

            val rotate = AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_clockwise)
            ImageView?.startAnimation(rotate)


    }
    fun stop_animation(ImageView : ImageView?){
        ImageView?.animation?.cancel()
    }

}