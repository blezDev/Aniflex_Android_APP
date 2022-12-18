package com.blez.aniplex_clone.Presentation.topAiring

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.Adapter.TopAiringAdapter
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.`interface`.AnimeInterface
import com.blez.aniplex_clone.data.TopAiringData
import com.blez.aniplex_clone.network.RetrofitInstance
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [TopAiringFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TopAiringFragment : Fragment() {
    private lateinit var adapter : TopAiringAdapter
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_airing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topProgressView = view.findViewById<ConstraintLayout>(R.id.progressView)
        val topProgressBar = view.findViewById<ImageView>(R.id.detailProgressBar)
        topProgressBar.visibility = View.VISIBLE
        rotate_animation(topProgressBar)
        val retService = RetrofitInstance.getRetrofitInstance()
            .create(AnimeInterface::class.java)
        val responseLiveData : LiveData<Response<TopAiringData>> = liveData {
            val response = retService.getTopAiring()
            emit(response)
        }

        responseLiveData.observe(viewLifecycleOwner, Observer {
            topProgressView.visibility = View.INVISIBLE
            val animeMovieList = it.body()
            if(animeMovieList!=null){
                adapter = TopAiringAdapter(requireContext(),animeMovieList)
                val movieView = view.findViewById<RecyclerView>(R.id.topAiringRecyclerView)
                movieView.adapter = adapter
                movieView.layoutManager = GridLayoutManager(requireContext(),2)


            }
        })
    }
    fun rotate_animation( ImageView : ImageView?){
        val rotate = AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_clockwise)
        ImageView?.startAnimation(rotate)
    }

}