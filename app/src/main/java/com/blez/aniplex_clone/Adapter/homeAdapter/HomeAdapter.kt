package com.blez.aniplex_clone.Adapter.homeAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.PopularModelItem
import com.blez.aniplex_clone.data.RecentData
import com.blez.aniplex_clone.data.SearchAnimeItem
import com.blez.aniplex_clone.databinding.AnimeImgListBinding
import com.bumptech.glide.Glide


class HomeAdapter(private val context: Context, private val animeList: List<SearchAnimeItem>) :
    RecyclerView.Adapter<HomeAdapter.ItemHolder>() {
    private lateinit var binding: AnimeImgListBinding
    var onItemClickText : ((SearchAnimeItem?) -> Unit)? = null
    inner class ItemHolder(val binding: AnimeImgListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.anime_img_list,
            parent,
            false
        )
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val data = animeList[position]
      binding.apply {
          movieName.text = data.animeTitle
          episodeText.text = data.status
          Glide.with(context).load(data.animeImg).into(recentView)
          binding.carView.setOnClickListener {
              Log.e("TAG","item clicked")
              onItemClickText?.invoke(data)
              /*     holder.bind(item,it)*/

          }
      }
    }

    override fun getItemCount(): Int {
        return animeList.size
    }
}