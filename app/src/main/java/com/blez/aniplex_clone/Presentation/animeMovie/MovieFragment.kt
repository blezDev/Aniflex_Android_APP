package com.blez.aniplex_clone.Presentation.animeMovie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.blez.aniplex_clone.Adapter.AnimeMoviesAdapter
import com.blez.aniplex_clone.Presentation.recentanimerelease.RecentAnimeViewModel
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private lateinit var adapter: AnimeMoviesAdapter
    private lateinit var binding : FragmentMovieBinding





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rotate_animation(binding.detailProgressBar)
        val recentAnimeViewModel = ViewModelProvider(this).get(RecentAnimeViewModel::class.java)
        adapter = AnimeMoviesAdapter(requireContext())
        binding.MovieRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        CoroutineScope(Dispatchers.Main).launch {
            recentAnimeViewModel.movieDataList
                .collectLatest {
                adapter.submitData(lifecycle,it)
                binding.progressView.isVisible = false
                    binding.MovieRecyclerView.adapter = adapter
            }
            }


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
        binding.searchBTN.setOnClickListener {
            findNavController().navigate(R.id.action_movieFragment_to_searchFragment)
        }

    }
    fun rotate_animation( ImageView : ImageView?){

            val rotate = AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_clockwise)
            ImageView?.startAnimation(rotate)


    }
    fun stop_animation(ImageView : ImageView?){
        ImageView?.animation?.cancel()
    }

}