package com.blez.aniplex_clone.Presentation.popularAnime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.blez.aniplex_clone.Adapter.PopularAnimeAdapter
import com.blez.aniplex_clone.Presentation.recentanimerelease.RecentAnimeViewModel
import com.blez.aniplex_clone.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PopularAnimeFragment : Fragment() {
    private lateinit var adapter : PopularAnimeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
           findNavController().navigate(R.id.recentReleaseFragment)
        }
        callback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular_anime, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val PopularProgressBar = view?.findViewById<ImageView>(R.id.PopularProgressBar)
        val progressView = view.findViewById<ConstraintLayout>(R.id.progressView)
        rotate_animation(PopularProgressBar)
       val recentAnimeViewModel = ViewModelProvider(this)[RecentAnimeViewModel::class.java]




          /*  progressView?.visibility = View.GONE
            stop_animation(PopularProgressBar)
            val animeMovieList = it.body()
            if (animeMovieList != null) {
                adapter = PopularAnimeAdapter(requireContext(), animeMovieList)
                val movieView = view.findViewById<RecyclerView>(R.id.PopularRecyclerView)
                movieView.adapter = adapter
                movieView.layoutManager = GridLayoutManager(requireContext(), 2)
                adapter.onItemClickText = {
                    val extras =
                        PopularAnimeFragmentDirections.actionPopularAnimeFragmentToDetailsFragment(
                            it?.animeId!!
                        )
                    findNavController().navigate(extras)
                }

            }
        */
    }
    fun rotate_animation( ImageView : ImageView?){

            val rotate = AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_clockwise)
            ImageView?.startAnimation(rotate)


    }
    fun stop_animation(ImageView : ImageView?){
        ImageView?.animation?.cancel()
    }


}