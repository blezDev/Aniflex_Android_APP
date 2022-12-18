package com.blez.aniplex_clone.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.RecentData
import com.bumptech.glide.Glide

class AnimePagingAdapter(private val context: Context) : PagingDataAdapter<RecentData,AnimePagingAdapter.ItemHolder>(COMPARATOR){
    var onItemClickImg : ((RecentData?) -> Unit)? = null
    var onItemClickText : ((RecentData?) -> Unit)? = null


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var episode_text: TextView = itemView.findViewById(R.id.episode_text)
        var anime_name = itemView.findViewById<TextView>(R.id.movie_name)
        var recent_view = itemView.findViewById<ImageView>(R.id.recent_view)
        var lang_text = itemView.findViewById<TextView>(R.id.lang_text)
    }




    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<RecentData>() {
            override fun areItemsTheSame(oldItem: RecentData, newItem: RecentData): Boolean {
                return oldItem.animeId ==newItem.animeId
            }

            override fun areContentsTheSame(oldItem: RecentData, newItem: RecentData): Boolean {
              return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
       val anime = getItem(position)
        if (anime != null) {
            Glide.with(context).load(anime.animeImg).into(holder.recent_view)
            holder.anime_name.text = anime.animeTitle
            holder.episode_text.text = "Episode No: ${anime.episodeNum}"
            holder.lang_text.text = anime.subOrDub
            holder.anime_name.setOnClickListener {
                onItemClickText?.invoke(anime)
            }
            holder.recent_view.setOnClickListener {
                onItemClickImg?.invoke(anime)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.anime_img_list,parent,false)
        return ItemHolder(view)
    }
}