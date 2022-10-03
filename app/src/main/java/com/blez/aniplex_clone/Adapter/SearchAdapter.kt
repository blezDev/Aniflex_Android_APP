package com.blez.aniplex_clone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.SearchAnime
import com.blez.aniplex_clone.data.SearchAnimeItem
import com.blez.aniplex_clone.databinding.AnimeListBinding
import com.bumptech.glide.Glide

class SearchAdapter(val searchAnimeList : SearchAnime,val context :Context) : RecyclerView.Adapter<SearchAdapter.ItemView>() {
    var onItemClick : ((SearchAnimeItem) -> Unit) ?= null
    private lateinit var binding: AnimeListBinding
    inner class ItemView(binding: AnimeListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
        binding= DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.anime_list,parent,false)
        return  ItemView(binding)

    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
        Glide.with(context).load(searchAnimeList[position].animeImg).into(binding.image)

        binding.AnimeTitleText.text = searchAnimeList[position].animeTitle
        binding.StatusText.text = searchAnimeList[position].status
        binding.apply {
            AnimeTitleText.setOnClickListener {
                onItemClick?.invoke(searchAnimeList[position])
            }
            image.setOnClickListener { onItemClick?.invoke(searchAnimeList[position]) }
        }
    }

    override fun getItemCount(): Int {
        return searchAnimeList.size
    }

}