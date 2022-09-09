package com.blez.aniplex_clone.Adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.data.Episodes
import com.blez.aniplex_clone.databinding.DetailListBinding

class EpisodeListAdapter(private val episodesList :List<Episodes>) : RecyclerView.Adapter<EpisodeListAdapter.ItemView>() {
    private lateinit var binding : DetailListBinding
    inner class ItemView(val binding : DetailListBinding ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
        binding = DetailListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemView(binding)
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
       with(holder){
           binding.DetailsEpisodeText.text = "Episode No: ${episodesList[position].episodeNum}"
       }
    }

    override fun getItemCount(): Int {
        return episodesList.size
    }


}