package com.blez.aniplex_clone.Adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.RecentData
import com.bumptech.glide.Glide

class RecentAnimeAdapter(val context: Context, val recentData: List<RecentData?>) : RecyclerView.Adapter<RecentAnimeAdapter.ItemHolder>(){
    var onItemClickImg : ((RecentData?) -> Unit)? = null
    var onItemClickText : ((RecentData?) -> Unit)? = null
    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var episode_text: TextView = itemView.findViewById(R.id.episode_text)
        var anime_name = itemView.findViewById<TextView>(R.id.movie_name)
        var recent_view = itemView.findViewById<ImageView>(R.id.recent_view)
        var lang_text = itemView.findViewById<TextView>(R.id.lang_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.anime_img_list,parent,false)
       return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
       Glide.with(context).load(recentData[position]?.animeImg).into(holder.recent_view)
        holder.anime_name.text = recentData[position]?.animeTitle
        holder.episode_text.text = "Episode No: ${recentData[position]?.episodeNum}"
        holder.lang_text.text = recentData[position]?.subOrDub
        holder.anime_name.setOnClickListener {
            onItemClickText?.invoke(recentData[position])
        }
        holder.recent_view.setOnClickListener {
            onItemClickImg?.invoke(recentData[position])
        }


    }

    override fun getItemCount(): Int {
        return recentData.size
    }
}