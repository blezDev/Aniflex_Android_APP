package com.blez.aniplex_clone.Presentation.recentanimerelease

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.Adapter.RecentAnimeAdapter
import com.blez.aniplex_clone.Presentation.common.VideoActivity
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.FragmentRecentAnimeBinding


class RecentReleaseFragment : Fragment() {
    private lateinit var binding: FragmentRecentAnimeBinding
    private lateinit var adapter: RecentAnimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_recent_anime, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.RecentProgressBar.visibility = View.VISIBLE
        val recentAnimeViewModel = ViewModelProvider(this)[RecentAnimeViewModel::class.java]//Contains the page number and retrofit instance

        pageChange(recentAnimeViewModel, view)
    }
    private fun pageChange(recentAnimeViewModel : RecentAnimeViewModel,view: View)
{
            recentAnimeViewModel.responseLiveData.observe(viewLifecycleOwner) {
                binding.RecentProgressBar.visibility = View.INVISIBLE
        val releaseAnimesList = it.body()//iterates the list in an proper sequence
        if (releaseAnimesList != null) {
            adapter = RecentAnimeAdapter(requireContext(), releaseAnimesList)
            val animeView = view.findViewById<RecyclerView>(R.id.RecentAnimeReleaseRecyclerView)
            animeView.adapter = adapter
            animeView.layoutManager = GridLayoutManager(requireContext(),2)
            adapter.onItemClickImg ={
                val intent = Intent(context, VideoActivity::class.java)
                intent.putExtra("episodeId",it?.episodeId)
                startActivity(intent)
            }
            adapter.onItemClickText = {
                val extras = RecentReleaseFragmentDirections.actionRecentReleaseFragmentToDetailsFragment(it?.animeId!!)
                findNavController().navigate(extras)
            }
        }
    }
}

}