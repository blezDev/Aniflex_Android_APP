package com.blez.aniplex_clone.Adapter.popularAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.PopularModelItem
import com.blez.aniplex_clone.databinding.RecentGridViewBinding
import com.bumptech.glide.Glide

class PopularAdapter(private val context : Context,private val data : List<PopularModelItem>) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    private lateinit var binding : RecentGridViewBinding
    inner class ViewHolder(binding : RecentGridViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recent_grid_view,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
       binding.apply {
           movieName.text = item.animeTitle
           Glide.with(context).load(item.animeImg).into(recentView)
       }
    }
}