package com.blez.aniplex_clone.Presentation.recentanimerelease

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.Adapter.RecentAnimeAdapter
import com.blez.aniplex_clone.Presentation.common.VideoActivity
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.VideoData
import com.blez.aniplex_clone.databinding.FragmentRecentAnimeBinding
import com.blez.aniplex_clone.utils.SettingManager
import retrofit2.Response


class RecentReleaseFragment : Fragment() {
    private lateinit var binding: FragmentRecentAnimeBinding
    private lateinit var adapter: RecentAnimeAdapter
    private lateinit var settingManager: SettingManager

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
        settingManager = SettingManager(requireContext())

        recentAnimeViewModel.responseLiveData.observe(viewLifecycleOwner) {
            binding.RecentProgressBar.visibility = View.INVISIBLE
            val releaseAnimesList = it.body()//iterates the list in an proper sequence
            if (releaseAnimesList != null) {
                adapter = RecentAnimeAdapter(requireContext(), releaseAnimesList)
                val animeView = view.findViewById<RecyclerView>(R.id.RecentAnimeReleaseRecyclerView)
                animeView.adapter = adapter
                animeView.layoutManager = GridLayoutManager(requireContext(),2)
                adapter.onItemClickImg ={
                    var videoPref = settingManager.getVideoPrefs()
                    if (videoPref.isNullOrEmpty()){
                        settingManager.saveVideoPreference("InApp")
                        videoPref = settingManager.getVideoPrefs()
                    }
                    when(videoPref){
                        "InApp" ->{
                            val intent = Intent(context, VideoActivity::class.java)
                            intent.putExtra("episodeId",it?.episodeId)
                            startActivity(intent)
                        }
                        "VLC"->{
                            val pathResponse : LiveData<Response<VideoData>> = liveData {
                                val response = it?.episodeId?.let { recentAnimeViewModel.retService.getVideoLink(episodeId = it) }
                                if (response != null) {
                                    Log.d("TAG", response.toString())
                                    emit(response)
                                }
                                else
                                {
                                    Toast.makeText(requireContext(),"not able to fetch", Toast.LENGTH_LONG).show()

                                }
                            }
                            pathResponse.observe(viewLifecycleOwner, Observer {
                                val uri = Uri.parse(it.body()?.sources?.get(0)?.file.toString())
                                val intent = Intent(Intent.ACTION_VIEW,uri)
                                intent.setPackage("org.videolan.vlc")
                                requireContext().startActivity(intent)
                            })

                        }
                }

                }
                adapter.onItemClickText = {
                    val extras = RecentReleaseFragmentDirections.actionRecentReleaseFragmentToDetailsFragment(it?.animeId!!)
                    findNavController().navigate(extras)
                }
            }
        }



    }
}