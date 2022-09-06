package com.blez.aniplex_clone.model.recentanimerelease

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.Adapter.RecentAnimeAdapter
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.`interface`.AnimeInterface
import com.blez.aniplex_clone.data.ReleaseAnimes
import com.blez.aniplex_clone.model.common.VideoActivity
import com.blez.aniplex_clone.network.RetrofitInstance
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecentReleaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecentReleaseFragment : Fragment() {
    private lateinit var adapter: RecentAnimeAdapter
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recent_anime, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retService = RetrofitInstance.getRetrofitInstance()
            .create(AnimeInterface::class.java)
        val responseLiveData : LiveData<Response<ReleaseAnimes>> = liveData {
            val response = retService.getRecentRelease()
            emit(response)
        }
        responseLiveData.observe(viewLifecycleOwner, Observer {
            val releaseAnimesList = it.body()//iterates the list in an proper sequence
            if(releaseAnimesList!= null){
                adapter = RecentAnimeAdapter(requireContext(), releaseAnimesList)
                val animeView = view.findViewById<RecyclerView>(R.id.RecentAnimeReleaseRecyclerView)
                animeView.layoutManager = LinearLayoutManager(requireContext())
                animeView.adapter = adapter
                adapter.onItemClick ={
                    val intent = Intent(context, VideoActivity::class.java)
                    intent.putExtra("episodeId",it?.episodeId)
                    startActivity(intent)
                }

            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecentReleaseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecentReleaseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}