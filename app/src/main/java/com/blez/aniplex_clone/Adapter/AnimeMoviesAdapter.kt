package com.blez.aniplex_clone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.MovieDataItem
import com.blez.aniplex_clone.databinding.PopularanimelistBinding
import com.bumptech.glide.Glide

class AnimeMoviesAdapter(val context : Context) : PagingDataAdapter<MovieDataItem, AnimeMoviesAdapter.ItemHolder>(COMPARATOR) {
    private lateinit var binding : PopularanimelistBinding
    inner class ItemHolder(binding : PopularanimelistBinding) : RecyclerView.ViewHolder(binding.root)


    companion object{
    val COMPARATOR = object : DiffUtil.ItemCallback<MovieDataItem>(){
        override fun areItemsTheSame(oldItem: MovieDataItem, newItem: MovieDataItem): Boolean {
         return oldItem.animeId == newItem.animeId
        }

        override fun areContentsTheSame(oldItem: MovieDataItem, newItem: MovieDataItem): Boolean {
           return oldItem == newItem
        }

    }
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setIsRecyclable(false)
        val items = getItem(position)!!
        binding.apply {
            Glide.with(context).load(items.animeImg).into(popularImg)
            animeTitle.text = items.animeTitle
            releaseDateText.text = items.releasedDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.popularanimelist,parent,false)

        return ItemHolder(binding)
    }
}
