package com.blez.aniplex_clone.Presentation.detailsAnime

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.app.ShareCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blez.aniplex_clone.Adapter.EpisodeListAdapter
import com.blez.aniplex_clone.Presentation.exoplayer.VideoPlayerActivity
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.AnimeDetails
import com.blez.aniplex_clone.databinding.FragmentDetailsBinding
import com.blez.aniplex_clone.db.WatHistory
import com.blez.aniplex_clone.db.dao.WatchedDao
import com.blez.aniplex_clone.utils.SettingManager
import com.blez.aniplex_clone.utils.navigateSafely
import com.blez.aniplex_clone.utils.rotateView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var adapter: EpisodeListAdapter

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var settingManager: SettingManager
    private val detailsViewModel: DetailsViewModel by viewModels()
    lateinit var details: AnimeDetails

    @Inject
    lateinit var dao: WatchedDao
    var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingManager = SettingManager(requireContext())
        binding.progressView.isVisible = true
        rotateView(binding.detailProgressBar, requireContext())
        /*        rotate_animation(binding.detailProgressBar)*/
        val viewModelFactory = arguments?.getString("animeId")!!
        subscribeToUI(viewModelFactory)
        detailsViewModel.getData()
    }


    private fun subscribeToUI(animeId: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            detailsViewModel.history.collect {
                detailsViewModel.getAnimeDetails(animeId)
                detailsViewModel.detailsPhase.collectLatest { events ->
                    when (events) {
                        is DetailsViewModel.SetupEvent.AnimeDetailsLoading -> {
                            binding.progressView.isVisible = true
                        }

                        is DetailsViewModel.SetupEvent.AnimeDetailsData -> {
                            binding.progressView.isVisible = false
                            setupViewData(events.data, it)
                            intentToVideo(events.data)

                        }

                        else -> Unit
                    }

                }
            }


        }

    }


    fun shareArticle(articleUrl: String) {
        val mimeType = "video/m3u8"
        ShareCompat.IntentBuilder(requireContext())
            .setType(mimeType)
            .setText(articleUrl)
            .startChooser()
    }

    fun rotate_animation(ImageView: ImageView?) {

        val rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_clockwise)
        ImageView?.startAnimation(rotate)


    }

    fun setupViewData(details2: AnimeDetails, watHistories: List<WatHistory>) {
        val details = details2.toDetailModel()
        if (details.releasedDate.toString().toInt() < 2020){
            settingManager.saveSuggestion(details.animeTitle.toString())
        }
        binding.apply {
            tupeText.isVisible = true
            StatusText.isVisible = true
            animeTitle.text = details.animeTitle
            tupeText.text = "Type : ${details?.type}"
            StatusText.text = "Status : ${details?.status}"
            releasedDateText.text = "Released Date : ${details?.releasedDate}"
            otherNames.text = "Other Name : ${details.otherNames}"
            genresText.text = "Genres :${details.genres?.joinToString(", ").toString()}"
            Glide.with(requireActivity().applicationContext)
                .load(details.animeImg.toString())
                .transform(CircleCrop())
                .into(animeImg)
            synopsisText.text = details.synopsis
            adapter = EpisodeListAdapter(details.episodesList ?: emptyList(), watHistories)
            adapter.differ.submitList(watHistories)
            adapter.notifyDataSetChanged()
            episodeListRecylcerView.adapter = adapter
            episodeListRecylcerView.layoutManager = LinearLayoutManager(requireContext())
            ViewCompat.setNestedScrollingEnabled(binding.episodeListRecylcerView, false)
        }
    }


    private fun intentToVideo(data: AnimeDetails) {


        val details = data.toDetailModel()
        adapter.onItemClickEpisode = {
            /*binding.progressView.visibility = View.VISIBLE
        rotate_animation(binding.detailProgressBar)*/
            val viewModelFactory = arguments?.getString("animeId")!!
            Log.e("TAG", "Start")
            val history = WatHistory(
                imgUrl = data.image,
                animeId = viewModelFactory,
                watchedEpiID = it?.episodeId ?: "",
                animeName = data.title
            )
            detailsViewModel.insertHistory(history)
            val intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putExtra("episodeId", it?.episodeId)
            findNavController()?.navigateSafely(
                R.id.action_detailsFragment_to_videoPlayerActivity,
                Bundle()?.apply { putString("episodeId", it?.episodeId) })
        }


    }


    fun stop_animation(ImageView: ImageView?) {
        ImageView?.animation?.cancel()
    }
}

