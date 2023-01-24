package com.blez.aniplex_clone.Adapter

import android.content.Context
import android.text.method.ScrollingMovementMethod
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
import com.blez.aniplex_clone.data.PopularData
import com.blez.aniplex_clone.data.PopularDataItem
import com.blez.aniplex_clone.data.RecentData
import com.blez.aniplex_clone.databinding.PopularanimelistBinding
import com.bumptech.glide.Glide

class PopularAnimeAdapter(val context: Context) : PagingDataAdapter<PopularDataItem, PopularAnimeAdapter.ItemView>(COMPARATOR){
    private lateinit var binding : PopularanimelistBinding

        var onItemClickText : ((PopularDataItem?) -> Unit)? = null
    inner class ItemView(binding : PopularanimelistBinding) : RecyclerView.ViewHolder(binding.root)




    companion object
    {
        val COMPARATOR = object : DiffUtil.ItemCallback<PopularDataItem>(){
            override fun areItemsTheSame(
                oldItem: PopularDataItem,
                newItem: PopularDataItem
            ): Boolean {
             return  oldItem.animeId == newItem.animeId
            }

            override fun areContentsTheSame(
                oldItem: PopularDataItem,
                newItem: PopularDataItem
            ): Boolean {
            return   oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
        holder.setIsRecyclable(false)
       val item = getItem(position)!!
        binding.apply {
            Glide.with(context).load(item.animeImg).into(popularImg)
            releaseDateText.text = item.releasedDate
            animeTitle.text = item.animeTitle
        }
        binding.popularImg.setOnClickListener {
            onItemClickText?.invoke(item)
        }
        binding.animeTitle.setOnClickListener {
            onItemClickText?.invoke(item)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.popularanimelist,parent,false)
        return ItemView(binding)
    }


}