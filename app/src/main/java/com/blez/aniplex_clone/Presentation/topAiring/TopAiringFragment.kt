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
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.blez.aniplex_clone.Adapter.TopAiringAdapter
import com.blez.aniplex_clone.Presentation.recentanimerelease.RecentAnimeViewModel
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.FragmentTopAiringBinding
import com.blez.aniplex_clone.utils.navigateSafely
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TopAiringFragment : Fragment() {
    private lateinit var adapter : TopAiringAdapter
    private lateinit var binding : FragmentTopAiringBinding
    private lateinit var recentAnimeViewModel: RecentAnimeViewModel


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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_top_airing, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recentAnimeViewModel = ViewModelProvider(this)[RecentAnimeViewModel::class.java]
        binding.topAiringRecyclerView.layoutManager =GridLayoutManager(requireContext(),2)
        adapter = TopAiringAdapter(requireContext())
        CoroutineScope(Dispatchers.Main).launch {
            binding.progressView.isVisible = false
            recentAnimeViewModel.topAiringList.collectLatest {
             adapter.submitData(lifecycle,it)
                binding.topAiringRecyclerView.adapter = adapter
            }



        }






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