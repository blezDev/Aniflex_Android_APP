package com.blez.aniplex_clone.Adapter.recentAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.ReleaseAnimes
import com.blez.aniplex_clone.databinding.RecentGridViewBinding
import com.bumptech.glide.Glide

class RecentGridAdapter(private val  context : Context,private val data : ReleaseAnimes?) : RecyclerView.Adapter<RecentGridAdapter.ItemHolder>() {
    private lateinit var binding : RecentGridViewBinding
    inner class ItemHolder(binding : RecentGridViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recent_grid_view,parent,false)
        return ItemHolder(binding)
    }

    override fun getItemCount(): Int {
       return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
      if (data != null)
      {
          val  item = data[position]
          binding.movieName.text = "${item.animeTitle} - ${item.episodeNum}"
          Glide.with(context).load(item.animeImg).into(binding.recentView)
      }
    }


}