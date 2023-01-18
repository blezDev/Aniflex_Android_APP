package com.blez.aniplex_clone.Adapter

import android.content.Context
import android.media.Image
import android.util.Log
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
import com.blez.aniplex_clone.data.RecentData
import com.blez.aniplex_clone.databinding.AnimeImgListBinding
import com.bumptech.glide.Glide

class RecentAnimeAdapter(val context: Context) : PagingDataAdapter<RecentData,RecentAnimeAdapter.ItemHolder>(
    COMPARATOR){
    var onItemClickImg : ((RecentData?) -> Unit)? = null
    var onItemClickText : ((RecentData?) -> Unit)? = null
    private lateinit var binding : AnimeImgListBinding
    inner class ItemHolder( binding : AnimeImgListBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: RecentData,view : View) {
            view.setOnClickListener {
                onItemClickText?.invoke(data)


            }
        }

    }

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<RecentData>(){
            override fun areItemsTheSame(oldItem: RecentData, newItem: RecentData): Boolean {
                return oldItem.animeId == newItem.animeId
            }

            override fun areContentsTheSame(oldItem: RecentData, newItem: RecentData): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {

            binding.movieName.text = item.animeTitle
            binding.episodeText.text = "Episode no :${item.episodeNum}"
            Glide.with(context).load(item.animeImg).into(binding.recentView)

            binding.movieName.setOnClickListener {
                Log.e("TAG","item clicked")
             onItemClickText?.invoke(item)
           /*     holder.bind(item,it)*/

            }
            binding.recentView.setOnClickListener {
                onItemClickImg?.invoke(item)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
      binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.anime_img_list,parent,false)
        return ItemHolder(binding)
    }


}