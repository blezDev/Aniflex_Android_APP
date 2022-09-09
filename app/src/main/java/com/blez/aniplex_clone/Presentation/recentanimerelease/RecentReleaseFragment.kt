package com.blez.aniplex_clone.Presentation.recentanimerelease

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.Adapter.RecentAnimeAdapter
import com.blez.aniplex_clone.Presentation.common.VideoActivity
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.FragmentRecentAnimeBinding




class RecentReleaseFragment : Fragment() {
    private lateinit var binding: FragmentRecentAnimeBinding
    private lateinit var adapter: RecentAnimeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

        val recentAnimeViewModel = ViewModelProvider(this)[RecentAnimeViewModel::class.java]//Contains the page number and retrofit instance

        binding.pageNoText.text = recentAnimeViewModel.page.toString()
        pageChange(recentAnimeViewModel, view)
    }
    private fun pageChange(recentAnimeViewModel : RecentAnimeViewModel,view: View)
{
    recentAnimeViewModel.responseLiveData.observe(viewLifecycleOwner) {

        val releaseAnimesList = it.body()//iterates the list in an proper sequence
        if (releaseAnimesList != null) {
            adapter = RecentAnimeAdapter(requireContext(), releaseAnimesList)
            val animeView = view.findViewById<RecyclerView>(R.id.RecentAnimeReleaseRecyclerView)

            binding.apply {
                pageNext.setOnClickListener {
                    recentAnimeViewModel.increment()//number increment
                    binding.pageNoText.text = recentAnimeViewModel.page.toString()
                }
                pagePrev.setOnClickListener {
                    recentAnimeViewModel.decrement()//number decrement
                    binding.pageNoText.text = recentAnimeViewModel.page.toString()
                }
            }
            animeView.adapter = adapter
            animeView.layoutManager = GridLayoutManager(requireContext(),2)
            adapter.onItemClick ={
                val intent = Intent(context, VideoActivity::class.java)
                intent.putExtra("episodeId",it?.episodeId)
                startActivity(intent)
            }

        }
    }
}

}