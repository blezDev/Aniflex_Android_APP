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
import com.blez.aniplex_clone.data.TopAiringData
import com.blez.aniplex_clone.data.TopAiringDataItem
import com.blez.aniplex_clone.databinding.PopularanimelistBinding
import com.bumptech.glide.Glide

class TopAiringAdapter(val context: Context) : PagingDataAdapter<TopAiringDataItem,TopAiringAdapter.ItemView>(COMPARATOR) {
    private lateinit var binding: PopularanimelistBinding

    inner class ItemView(binding: PopularanimelistBinding) : RecyclerView.ViewHolder(binding.root)


    companion object{
        val COMPARATOR = object : DiffUtil.ItemCallback<TopAiringDataItem>(){
            override fun areItemsTheSame(
                oldItem: TopAiringDataItem,
                newItem: TopAiringDataItem
            ): Boolean {
                return oldItem.animeId == newItem.animeId
            }

            override fun areContentsTheSame(
                oldItem: TopAiringDataItem,
                newItem: TopAiringDataItem
            ): Boolean {
              return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
        holder.setIsRecyclable(false)
      val item = getItem(position)
        binding.apply {
            Glide.with(context).load(item?.animeImg).into(popularImg)
            animeTitle.text = item?.animeTitle
            releaseDateText.text = item?.latestEp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
    binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.popularanimelist,parent,false)
        return ItemView(binding)
    }


}

