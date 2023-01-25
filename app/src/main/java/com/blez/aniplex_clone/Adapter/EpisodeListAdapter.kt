package com.blez.aniplex_clone.Adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.Episodes
import com.blez.aniplex_clone.data.RecentData
import com.blez.aniplex_clone.databinding.DetailListBinding

class EpisodeListAdapter(private val episodesList :List<Episodes>) : RecyclerView.Adapter<EpisodeListAdapter.ItemView>() {
    private lateinit var binding : DetailListBinding
    var onItemClickEpisode : ((Episodes?) -> Unit)? = null
    var onItemClickDownload : ((Episodes?) -> Unit)? = null
    inner class ItemView(val binding : DetailListBinding ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.detail_list,parent,false)
        return ItemView(binding)
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
        holder.setIsRecyclable(false)
       with(binding){
          episodeText.text = "Episode ${episodesList[position].episodeNum}"

           episodeText.setOnClickListener {
               onItemClickEpisode?.invoke(episodesList[position])
           }

          downloadBTN.setOnClickListener {
              onItemClickDownload?.invoke(episodesList[position])

           }
       }
    }

    override fun getItemCount(): Int {
        return episodesList.size
    }


}