package com.blez.aniplex_clone.Presentation.recentanimerelease

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.blez.aniplex_clone.Adapter.RecentAnimeAdapter
import com.blez.aniplex_clone.Presentation.common.VideoActivity
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.VideoData
import com.blez.aniplex_clone.databinding.FragmentRecentAnimeBinding
import com.blez.aniplex_clone.paging.LoaderAdapter
import com.blez.aniplex_clone.utils.Constants.IN_APP
import com.blez.aniplex_clone.utils.Constants.VLC
import com.blez.aniplex_clone.utils.SettingManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import retrofit2.Response

@AndroidEntryPoint
class RecentReleaseFragment : Fragment() {
    private lateinit var binding: FragmentRecentAnimeBinding
    private lateinit var adapter: RecentAnimeAdapter
    private lateinit var settingManager: SettingManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recent_anime, container, false)
        // Inflate the layout for this fragment
        adapter = RecentAnimeAdapter(requireContext())
        Log.e("TAG","onCreateView is called")


        rotate_animation(binding.RecentProgressBar)
        val recentAnimeViewModel = ViewModelProvider(this).get(RecentAnimeViewModel::class.java)//Contains the page number and retrofit instance
        settingManager = SettingManager(requireContext())
        binding.progressView.visibility = View.VISIBLE


        adapter.onItemClickImg = {
            binding.RecentProgressBar.visibility = View.VISIBLE
            var videoPref = settingManager.getVideoPrefs()
            if (videoPref.isNullOrEmpty()) {
                settingManager.saveVideoPreference(IN_APP)
                videoPref = settingManager.getVideoPrefs()
            }
            when (videoPref) {
                IN_APP -> {
                    val intent = Intent(context, VideoActivity::class.java)
                    intent.putExtra("episodeId", it?.episodeId)
                    startActivity(intent)
                }
                VLC -> {
                 CoroutineScope(Dispatchers.Main).launch {
                        val response = recentAnimeViewModel.getVideoLink(it?.episodeId.toString()).await()

                        val it = response
                        binding.RecentProgressBar.visibility = View.GONE
                        val uri = Uri.parse(it?.sources_bk?.get(0)?.file.toString())
                        val intent = Intent(Intent.ACTION_VIEW,uri)
                        requireContext().startActivity(intent)
                    }





                }

            }

        }



        adapter.onItemClickText = {
            val extras = RecentReleaseFragmentDirections.actionRecentReleaseFragmentToDetailsFragment(it?.animeId!!)
            findNavController().navigate(extras)
            Log.e("TAG", "onItemTextClicked on RecentFragment")
        }
        val animeView = binding.RecentAnimeReleaseRecyclerView
        animeView.layoutManager = GridLayoutManager(requireContext(), 2)
        lifecycleScope.launch {
            recentAnimeViewModel.list.collectLatest {
                binding.progressView.visibility = View.INVISIBLE
                if (it != null) {
                    adapter.submitData(lifecycle, it)
                    animeView.adapter = adapter.withLoadStateHeaderAndFooter(
                        header = LoaderAdapter(),
                        footer = LoaderAdapter()
                    )
                }
            }
        }








        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    fun rotate_animation(ImageView: ImageView?) {
        val rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_clockwise)
        ImageView?.startAnimation(rotate)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}