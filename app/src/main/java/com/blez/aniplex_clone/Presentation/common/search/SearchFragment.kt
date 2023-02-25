package com.blez.aniplex_clone.Presentation.common.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.blez.aniplex_clone.Adapter.SearchAdapter
import com.blez.aniplex_clone.Presentation.recentanimerelease.RecentAnimeViewModel
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.FragmentSearchBinding
import com.blez.aniplex_clone.utils.navigateSafely
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding
    private lateinit var adapter : SearchAdapter
    private val recentAnimeViewModel : RecentAnimeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rotate_animation(binding.RecentProgressBar)

        binding.progressView.isVisible = false
        val text = arguments?.getString("animeQuery")
        adapter = SearchAdapter(null,requireContext())
        binding.searchRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.editSearchAnime.addTextChangedListener {
            searchQuery(it.toString(),recentAnimeViewModel)
        }



        super.onViewCreated(view, savedInstanceState)
    }
    fun rotate_animation( ImageView : ImageView?){

            val rotate = AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_clockwise)
            ImageView?.startAnimation(rotate)


    }
    fun stop_animation(ImageView : ImageView?){
        ImageView?.animation?.cancel()
    }

private fun searchQuery(search: String, recentAnimeViewModel: RecentAnimeViewModel){

    binding.progressView.isVisible = true
    CoroutineScope(Dispatchers.Main).launch {
        val it = recentAnimeViewModel.getSearchData(search).await()
        val animeList = it
        if(animeList!=null && animeList.isNotEmpty()){
            binding.progressView.visibility = View.INVISIBLE
            stop_animation(binding.RecentProgressBar)
            adapter = SearchAdapter(animeList,requireContext())
            adapter.notifyDataSetChanged()
            binding.searchRecyclerView.adapter = adapter
            binding.searchRecyclerView.adapter?.notifyDataSetChanged()

            adapter.onItemClick = {it->
                findNavController().navigateSafely(R.id.action_searchFragment_to_detailsFragment,Bundle().apply { putString("animeId",it.animeId)  })
            }

        }else{

            binding.progressView.visibility = View.INVISIBLE
        }
    }
}

}