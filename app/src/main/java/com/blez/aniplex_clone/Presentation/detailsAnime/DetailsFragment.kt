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
import androidx.core.app.ShareCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blez.aniplex_clone.Adapter.EpisodeListAdapter
import com.blez.aniplex_clone.Presentation.exoplayer.VideoPlayerActivity
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.FragmentDetailsBinding
import com.blez.aniplex_clone.utils.Constants
import com.blez.aniplex_clone.utils.SettingManager
import com.blez.aniplex_clone.utils.navigateSafely
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var adapter: EpisodeListAdapter

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var settingManager: SettingManager

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


/*        rotate_animation(binding.detailProgressBar)*/
        val viewModelFactory = arguments?.getString("animeId") !!
        val detailsViewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        CoroutineScope(Dispatchers.IO).launch {

        }

       CoroutineScope(Dispatchers.Main).launch {
            val details = detailsViewModel.getAnimeDetails(viewModelFactory).await()

           if (details != null) {
               try {
                   binding.apply {

                       animeTitle.text = details.animeTitle
                       tupeText.text = "Type : ${details?.type}"
                       StatusText.text = "Status : ${details?.status}"
                       releasedDateText.text = "Released Date : ${details?.releasedDate}"
                       otherNames.text = "Other Name : ${details.otherNames}"
                       genresText.text = "Genres :${details.genres.joinToString(", ").toString()}"
                       Glide.with(requireActivity().applicationContext)
                           .load(details.animeImg.toString()).into(animeImg)
                       synopsisText.text = details.synopsis
                       adapter = EpisodeListAdapter(details.episodesList)
                       episodeListRecylcerView.adapter = adapter
                       episodeListRecylcerView.layoutManager = LinearLayoutManager(requireContext())
                       ViewCompat.setNestedScrollingEnabled(binding.episodeListRecylcerView, false)
                       var videoPref = settingManager.getVideoPrefs()
                       adapter.onItemClickEpisode = {
                           /*binding.progressView.visibility = View.VISIBLE
                            rotate_animation(binding.detailProgressBar)*/

                           when (videoPref) {
                               Constants.IN_APP -> {
                                   val intent = Intent(context, VideoPlayerActivity::class.java)
                                   intent.putExtra("episodeId", it?.episodeId)
                                   startActivity(intent)
                               }
                               Constants.VLC -> {

                                   CoroutineScope(Dispatchers.Main).async {
                                       val it = detailsViewModel.getVideoLink(it.toString()).await()
                                       /*  binding.progressView.visibility = View.INVISIBLE*/
                                       val uri = Uri.parse(it?.sources?.get(0)?.file.toString())
                                       /*           shareArticle(uri.toString())*/
                                       val intent = Intent(Intent.ACTION_VIEW, uri)
                                       requireContext().startActivity(intent)
                                   }


                               }
                           }


                       }


                   }
               }catch (e : Exception)
               {
                   Snackbar.make(requireView(),"Server Error",Snackbar.LENGTH_LONG).show()
               }

           }
           else
           {
               Snackbar.make(requireView(),"Server Error",Snackbar.LENGTH_LONG).show()
               requireActivity().getOnBackPressedDispatcher()


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

        fun stop_animation(ImageView: ImageView?) {
            ImageView?.animation?.cancel()
        }
    }
}