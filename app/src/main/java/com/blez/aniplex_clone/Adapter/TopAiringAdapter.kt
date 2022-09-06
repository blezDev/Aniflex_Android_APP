package com.blez.aniplex_clone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.TopAiringData
import com.bumptech.glide.Glide

class TopAiringAdapter(val context: Context,val topAiringData : TopAiringData) : RecyclerView.Adapter<TopAiringAdapter.ItemView>(){
    inner class ItemView(itemView: View) : RecyclerView.ViewHolder(itemView){
        val topAiringImg = itemView.findViewById<ImageView>(R.id.topAiringImg)
        val gerneText = itemView.findViewById<TextView>(R.id.gerneText)
        val TopAiringanimeTitle = itemView.findViewById<TextView>(R.id.TopAiringanimeTitle)
        val latestEpiText = itemView.findViewById<TextView>(R.id.latestEpiText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topairinglist,parent,false)
        return ItemView(view)
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
      Glide.with(context).load(topAiringData[position].animeImg).into(holder.topAiringImg)
        holder.TopAiringanimeTitle.text = topAiringData[position].animeTitle
        holder.gerneText.text = topAiringData[position].genres.toString()
        holder.latestEpiText.text = topAiringData[position].latestEp

    }

    override fun getItemCount(): Int {
        return topAiringData.size
    }
}