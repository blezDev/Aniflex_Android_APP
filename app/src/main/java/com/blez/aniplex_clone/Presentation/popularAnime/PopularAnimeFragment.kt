package com.blez.aniplex_clone.Presentation.popularAnime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.blez.aniplex_clone.Adapter.PopularAnimeAdapter
import com.blez.aniplex_clone.Presentation.recentanimerelease.RecentAnimeViewModel
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.FragmentPopularAnimeBinding
import com.blez.aniplex_clone.utils.navigateSafely
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PopularAnimeFragment : Fragment() {
    private lateinit var adapter : PopularAnimeAdapter
    private lateinit var binding : FragmentPopularAnimeBinding
    private lateinit var recentAnimeViewModel: RecentAnimeViewModel
    private lateinit var popularAnimeAdapter: PopularAnimeAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_popular_anime, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       recentAnimeViewModel = ViewModelProvider(this)[RecentAnimeViewModel::class.java]
        adapter = PopularAnimeAdapter(requireContext())
        CoroutineScope(Dispatchers.Main).launch {
            recentAnimeViewModel.popularList.collectLatest {
                adapter.submitData(lifecycle,it)
                binding.PopularRecyclerView.adapter = adapter
            binding.progressView.isVisible = false

            }
        }

        binding.PopularRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)

        adapter.onItemClickText = {
            val bundle = bundleOf("animeId" to it?.animeId)
            findNavController()?.navigateSafely(R.id.action_popularAnimeFragment_to_detailsFragment,bundle)
        }





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
        binding.searchBTN.setOnClickListener {
            findNavController().navigateSafely(R.id.action_popularAnimeFragment_to_searchFragment)
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