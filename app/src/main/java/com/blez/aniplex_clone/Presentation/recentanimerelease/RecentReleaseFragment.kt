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
import com.blez.aniplex_clone.utils.Constants.IN_APP
import com.blez.aniplex_clone.utils.Constants.VLC
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
        rotate_animation(binding.RecentProgressBar)
        val recentAnimeViewModel = ViewModelProvider(this)[RecentAnimeViewModel::class.java]//Contains the page number and retrofit instance
        settingManager = SettingManager(requireContext())

        recentAnimeViewModel.responseLiveData.observe(viewLifecycleOwner) {
            binding.progressView.visibility = View.INVISIBLE
            val releaseAnimesList = it.body()//iterates the list in an proper sequence
            if (releaseAnimesList != null) {
                adapter = RecentAnimeAdapter(requireContext(), releaseAnimesList)
                val animeView = view.findViewById<RecyclerView>(R.id.RecentAnimeReleaseRecyclerView)
                animeView.adapter = adapter
                animeView.layoutManager = GridLayoutManager(requireContext(),2)
                adapter.onItemClickImg ={
                    binding.RecentProgressBar.visibility = View.VISIBLE
                    var videoPref = settingManager.getVideoPrefs()
                    if (videoPref.isNullOrEmpty()){
                        settingManager.saveVideoPreference(IN_APP)
                        videoPref = settingManager.getVideoPrefs()
                    }
                    when(videoPref){
                        IN_APP ->{
                            val intent = Intent(context, VideoActivity::class.java)
                            intent.putExtra("episodeId",it?.episodeId)
                            startActivity(intent)
                        }
                        VLC->{
                            val pathResponse : LiveData<Response<VideoData>> = liveData {

                                val response = it?.episodeId?.let { recentAnimeViewModel.retService.getVideoLink(episodeId = it) }
                                if (response != null) {
                                    Log.d("TAG", response.toString())
                                    emit(response)
                                }
                                else
                                {
                                    binding.RecentProgressBar.visibility = View.GONE
                                    Toast.makeText(requireContext(),"not able to fetch", Toast.LENGTH_LONG).show()

                                }
                            }
                            pathResponse.observe(viewLifecycleOwner, Observer {
                                binding.RecentProgressBar.visibility = View.GONE
                                val uri = Uri.parse(it.body()?.sources?.get(0)?.file.toString())
                                val intent = Intent(Intent.ACTION_VIEW,uri)
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
    fun rotate_animation( ImageView : ImageView?){
        val rotate = AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_clockwise)
        ImageView?.startAnimation(rotate)
    }
}