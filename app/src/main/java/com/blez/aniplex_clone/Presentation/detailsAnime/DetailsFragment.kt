package com.blez.aniplex_clone.Presentation.detailsAnime

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.blez.aniplex_clone.Adapter.EpisodeListAdapter
import com.blez.aniplex_clone.Presentation.common.VideoActivity
import com.blez.aniplex_clone.Presentation.recentanimerelease.RecentAnimeViewModel
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.VideoData
import com.blez.aniplex_clone.databinding.FragmentDetailsBinding
import com.blez.aniplex_clone.utils.Constants
import com.blez.aniplex_clone.utils.SettingManager
import com.bumptech.glide.Glide
import retrofit2.Response


class DetailsFragment : Fragment() {
    private lateinit var adapter: EpisodeListAdapter
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var settingManager: SettingManager

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingManager = SettingManager(requireContext())
        val recentAnimeViewModel = ViewModelProvider(this)[RecentAnimeViewModel::class.java]//Contains the page number and retrofit instance

        rotate_animation(binding.detailProgressBar)
        val viewModelFactory = DetailViewModelFactory(args.animeId)
        val detailsViewModel =
            ViewModelProvider(this, viewModelFactory)[DetailsViewModel::class.java]
        detailsViewModel.responseLiveData.observe(viewLifecycleOwner) {
            val details = it.body()
            binding.progressView.visibility = View.INVISIBLE
            binding.apply {

                animeTitle.text = details?.animeTitle
                tupeText.text = "Type : ${details?.type}"
                StatusText.text = "Status : ${details?.status}"
                releasedDateText.text = "Released Date : ${details?.releasedDate}"
                otherNames.text = "Other Name : ${details?.otherNames}"
                genresText.text = "Genres :${details?.genres?.joinToString(", ").toString()}"
                Glide.with(requireContext()).load(details?.animeImg.toString()).into(animeImg)
                synopsisText.text = details?.synopsis
                adapter = EpisodeListAdapter(details?.episodesList!!)
                episodeListRecylcerView.adapter = adapter
                episodeListRecylcerView.layoutManager = GridLayoutManager(requireContext(), 4)
                var videoPref = settingManager.getVideoPrefs()
                adapter.onItemClickEpisode = {
                    binding.progressView.visibility = View.VISIBLE
                    rotate_animation(binding.detailProgressBar)

                    when (videoPref) {
                        Constants.IN_APP -> {
                            val intent = Intent(context, VideoActivity::class.java)
                            intent.putExtra("episodeId", it?.episodeId)
                            startActivity(intent)
                        }
                        Constants.VLC -> {

                            val pathResponse: LiveData<Response<VideoData>> = liveData {
                                val response =
                                    it?.episodeId?.let { recentAnimeViewModel.retService.getVideoLink(episodeId = it) }
                                if (response != null) {
                                    Log.d("TAG", response.toString())
                                    emit(response)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "not able to fetch",
                                        Toast.LENGTH_LONG
                                    ).show()

                                }
                            }
                            pathResponse.observe(viewLifecycleOwner, Observer {
                                binding.progressView.visibility = View.INVISIBLE
                                stop_animation(binding.detailProgressBar)

                                val uri = Uri.parse(it.body()?.sources?.get(0)?.file.toString())
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                requireContext().startActivity(intent)
                            })

                        }
                    }


                }
            }


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