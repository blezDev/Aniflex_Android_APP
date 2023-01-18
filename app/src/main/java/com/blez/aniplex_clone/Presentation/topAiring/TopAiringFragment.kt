package com.blez.aniplex_clone.Presentation.topAiring

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.blez.aniplex_clone.Adapter.TopAiringAdapter
import com.blez.aniplex_clone.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [TopAiringFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class TopAiringFragment : Fragment() {
    private lateinit var adapter : TopAiringAdapter
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_top_airing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topProgressView = view.findViewById<ConstraintLayout>(R.id.progressView)
        val topProgressBar = view.findViewById<ImageView>(R.id.detailProgressBar)
        topProgressBar.visibility = View.VISIBLE
        rotate_animation(topProgressBar)



        /*    topProgressView.visibility = View.INVISIBLE
            stop_animation(topProgressBar)
            val animeMovieList = it.body()
            if(animeMovieList!=null){
                adapter = TopAiringAdapter(requireContext(),animeMovieList)
                val movieView = view.findViewById<RecyclerView>(R.id.topAiringRecyclerView)
                movieView.adapter = adapter
                movieView.layoutManager = GridLayoutManager(requireContext(),2)


            }*/

    }
    fun rotate_animation( ImageView : ImageView?){
        while (true){
            val rotate = AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_clockwise)
            ImageView?.startAnimation(rotate)
        }

    }
    fun stop_animation(ImageView : ImageView?){
        ImageView?.animation?.cancel()
    }

}