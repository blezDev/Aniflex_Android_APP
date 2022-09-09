package com.blez.aniplex_clone.Presentation.recentanimerelease

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.Adapter.RecentAnimeAdapter
import com.blez.aniplex_clone.Presentation.common.VideoActivity
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.ReleaseAnimes
import com.blez.aniplex_clone.databinding.FragmentRecentPageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecentPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecentPageFragment : Fragment() {
    private lateinit var adapter: RecentAnimeAdapter
    private lateinit var binding : FragmentRecentPageBinding
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
        return inflater.inflate(R.layout.fragment_recent_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recentAnimeViewModel = ViewModelProvider(this)[RecentAnimeViewModel::class.java]
        val pageNoText = view.findViewById<TextView>(R.id.pageNoText)
        pageNoText.text = recentAnimeViewModel.page.toString()
        pageChange(recentAnimeViewModel,view)


    }
    private fun pageChange(recentAnimeViewModel : RecentAnimeViewModel,view: View)
    {
        recentAnimeViewModel.responseLiveData.observe(viewLifecycleOwner, Observer {

            val releaseAnimesList = it.body()//iterates the list in an proper sequence
            if(releaseAnimesList!= null){

                adapterData(requireContext(),releaseAnimesList,view)

            }
        })
    }

    private fun adapterData(context: Context, releaseAnimesList: ReleaseAnimes, view: View) {
        adapter = RecentAnimeAdapter(requireContext(), releaseAnimesList)
        val animeView = view.findViewById<RecyclerView>(R.id.RecentAnimeReleaseRecyclerView)
        animeView.layoutManager = GridLayoutManager(requireContext(),2)
        animeView.adapter = adapter
        adapter.onItemClick ={
            val intent = Intent(context, VideoActivity::class.java)
            intent.putExtra("episodeId",it?.episodeId)
            startActivity(intent)
        }

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecentPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecentPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}