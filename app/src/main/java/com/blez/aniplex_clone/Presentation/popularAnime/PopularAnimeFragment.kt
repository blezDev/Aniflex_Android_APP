package com.blez.aniplex_clone.Presentation.popularAnime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.Adapter.PopularAnimeAdapter
import com.blez.aniplex_clone.Presentation.recentanimerelease.RecentAnimeViewModel
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.PopularData
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PopularAnimeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PopularAnimeFragment : Fragment() {
    private lateinit var adapter : PopularAnimeAdapter
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
        return inflater.inflate(R.layout.fragment_popular_anime, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val PopularProgressBar = view.findViewById<ProgressBar>(R.id.PopularProgressBar)
        PopularProgressBar.visibility = View.VISIBLE
       val recentAnimeViewModel = ViewModelProvider(this)[RecentAnimeViewModel::class.java]
        val responseLiveData : LiveData<Response<PopularData>> = liveData {
            val response = recentAnimeViewModel.retService.getPopularAnime()
            emit(response)
        }

        responseLiveData.observe(viewLifecycleOwner, {
            PopularProgressBar.visibility = View.INVISIBLE
            val animeMovieList = it.body()
            if(animeMovieList!=null){
                adapter = PopularAnimeAdapter(requireContext(),animeMovieList)
                val movieView = view.findViewById<RecyclerView>(R.id.PopularRecyclerView)
                movieView.adapter = adapter
                movieView.layoutManager = GridLayoutManager(requireContext(),2)
                adapter.onItemClickText = {
                    val extras = PopularAnimeFragmentDirections.actionPopularAnimeFragmentToDetailsFragment(it?.animeId!!)
                    findNavController().navigate(extras)
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
         * @return A new instance of fragment PopularAnimeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PopularAnimeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}