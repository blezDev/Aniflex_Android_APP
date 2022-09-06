package com.blez.aniplex_clone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.MovieDataItem
import com.bumptech.glide.Glide

class AnimeMoviesAdapter(val context : Context,val movieData : List<MovieDataItem>) : RecyclerView.Adapter<AnimeMoviesAdapter.ItemHolder>() {
    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var anime_name = itemView.findViewById<TextView>(R.id.movie_name)
        var movie_view = itemView.findViewById<ImageView>(R.id.movie_view)
        var release_text = itemView.findViewById<TextView>(R.id.release_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list,parent,false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        Glide.with(context).load(movieData[position].animeImg).into(holder.movie_view)
        holder.anime_name.text = movieData[position].animeTitle
        holder.release_text.text = movieData[position].releasedDate
    }

    override fun getItemCount(): Int {
        return movieData.size
    }
}