package com.blez.aniplex_clone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.CartItemBinding
import com.blez.aniplex_clone.db.WatHistory
import com.bumptech.glide.Glide

class HistoryAdapter(private val context : Context,val data : List<WatHistory>) : RecyclerView.Adapter<HistoryAdapter.ItemViewHolder>()  {
    private lateinit var binding : CartItemBinding
    class ItemViewHolder(binding : CartItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
      binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.cart_item,parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
       holder.setIsRecyclable(false)
        binding.apply {
            Glide.with(context).load(data[position].imgUrl).into(pImage)
            pName.text = data[position].animeId
            pCompanyName.isVisible = false
        }
    }


}