package com.blez.aniplex_clone.Adapter


import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.Episodes
import com.blez.aniplex_clone.databinding.DetailListBinding
import com.blez.aniplex_clone.db.WatHistory

class EpisodeListAdapter(private val episodesList :List<Episodes>,val episodeHistory : List<WatHistory>) : RecyclerView.Adapter<EpisodeListAdapter.ItemView>() {
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

        try {

            val data = episodesList[position].episodeId
            if (differ.currentList.isNotEmpty())
            if (differ.currentList.filter { it.watchedEpiID == data }.isNotEmpty())
            {
                binding.episodeText.setTextColor(Color.GRAY)
            }else
                binding.episodeText.setTextColor(Color.BLACK)


            with(binding){
                episodeText.text = "Episode ${episodesList[position].episodeNum}"

                episodeText.setOnClickListener {
                    binding.episodeText.setTextColor(Color.GRAY)
                    onItemClickEpisode?.invoke(episodesList[position])
                }

                downloadBTN.setOnClickListener {
                    onItemClickDownload?.invoke(episodesList[position])

                }
            }

        }catch (e : Exception)
        {
            Log.e("TAG",e.message.toString())
        }



    }

    private val diffCallback = object : DiffUtil.ItemCallback<WatHistory>(){
        override fun areItemsTheSame(oldItem: WatHistory, newItem: WatHistory): Boolean {
            return  oldItem.watchedEpiID == newItem.watchedEpiID
        }

        override fun areContentsTheSame(oldItem: WatHistory, newItem: WatHistory): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,diffCallback)

    override fun getItemCount(): Int {
        return episodesList.size
    }


}