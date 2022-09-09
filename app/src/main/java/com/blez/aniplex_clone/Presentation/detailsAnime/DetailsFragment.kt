package com.blez.aniplex_clone.Presentation.detailsAnime

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.blez.aniplex_clone.Adapter.EpisodeListAdapter
import com.blez.aniplex_clone.Presentation.common.VideoActivity
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.FragmentDetailsBinding
import com.bumptech.glide.Glide


class DetailsFragment : Fragment() {
    private lateinit var adapter : EpisodeListAdapter
    private val args : DetailsFragmentArgs by navArgs()
    private lateinit var binding : FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = DetailViewModelFactory(args.animeId)
        val detailsViewModel = ViewModelProvider(this,viewModelFactory)[DetailsViewModel::class.java]
       detailsViewModel.responseLiveData.observe(viewLifecycleOwner) {
           val details = it.body()
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
               episodeListRecylcerView.layoutManager = GridLayoutManager(requireContext(),4)
               adapter.onItemClickEpisode = {
                   val intent = Intent(context, VideoActivity::class.java)
                   intent.putExtra("episodeId",it?.episodeId)
                   startActivity(intent)
               }


           }
       }


    }
}