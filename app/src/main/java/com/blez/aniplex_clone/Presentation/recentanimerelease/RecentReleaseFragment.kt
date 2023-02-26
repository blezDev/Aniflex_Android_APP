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
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.blez.aniplex_clone.Adapter.RecentAnimeAdapter
import com.blez.aniplex_clone.Presentation.exoplayer.VideoPlayerActivity
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.FragmentRecentAnimeBinding
import com.blez.aniplex_clone.paging.LoaderAdapter
import com.blez.aniplex_clone.utils.Constants.IN_APP
import com.blez.aniplex_clone.utils.Constants.VLC
import com.blez.aniplex_clone.utils.SettingManager
import com.blez.aniplex_clone.utils.navigateSafely
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecentReleaseFragment : Fragment() {
    private lateinit var binding: FragmentRecentAnimeBinding
    private lateinit var adapter: RecentAnimeAdapter
    private lateinit var settingManager: SettingManager
    private val recentAnimeViewModel : RecentAnimeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recent_anime, container, false)
        // Inflate the layout for this fragment

        return binding.root
    }






    fun rotate_animation(ImageView: ImageView?) {
        val rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_clockwise)
        ImageView?.startAnimation(rotate)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecentAnimeAdapter(requireContext())
        Log.e("TAG", "onCreateView is called")
        binding.progressView.isVisible = true

        settingManager = SettingManager(requireContext())
        binding.progressView.visibility = View.VISIBLE
        rotate_animation(binding.RecentProgressBar)


        adapter.onItemClickImg = {
            binding.RecentProgressBar.visibility = View.VISIBLE
            var videoPref = settingManager.getVideoPrefs()
            if (videoPref.isNullOrEmpty()) {
                settingManager.saveVideoPreference(IN_APP)
                videoPref = settingManager.getVideoPrefs()
            }
            when (videoPref) {
                IN_APP -> {
                    val intent = Intent(context, VideoPlayerActivity::class.java)
                    intent.putExtra("episodeId", it?.episodeId)
                    startActivity(intent)

                }
                VLC -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        val response = recentAnimeViewModel.getVideoLink(it?.episodeId.toString())
                        binding.progressView.isVisible = false
                        val it = response
                        binding.RecentProgressBar.visibility = View.GONE
                        val uri = Uri.parse(it?.sources_bk?.get(0)?.file.toString())
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        requireContext().startActivity(intent)
                    }


                }

            }

        }



        adapter.onItemClickText = { it ->

            findNavController().navigateSafely(
                R.id.action_recentReleaseFragment_to_detailsFragment, Bundle().apply { putString("animeId", it?.animeId) })
            Log.e("TAG", "onItemTextClicked on RecentFragment")
        }
        val animeView = binding.RecentAnimeReleaseRecyclerView
        animeView.layoutManager = GridLayoutManager(requireContext(), 2)
        lifecycleScope.launch {
            recentAnimeViewModel.list.distinctUntilChanged()
                .onStart {
                    binding.progressView.isVisible = false
                }
                .collect {
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





    }
}