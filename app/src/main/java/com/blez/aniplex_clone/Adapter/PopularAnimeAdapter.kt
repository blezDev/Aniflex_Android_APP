package com.blez.aniplex_clone.Adapter

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.data.PopularData
import com.blez.aniplex_clone.data.PopularDataItem
import com.blez.aniplex_clone.data.RecentData
import com.bumptech.glide.Glide

class PopularAnimeAdapter(val context: Context, private val popularData: PopularData) : RecyclerView.Adapter<PopularAnimeAdapter.ItemView>() {

        var onItemClickText : ((PopularDataItem?) -> Unit)? = null
    inner class ItemView(itemView: View) : RecyclerView.ViewHolder(itemView){
        val animeImgView = itemView.findViewById<ImageView>(R.id.animeImgView)
        val AnimeTitleText = itemView.findViewById<TextView>(R.id.AnimeTitleText)
        val releaseDateText = itemView.findViewById<TextView>(R.id.releaseDateText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popularanimelist,parent,false)
        return ItemView(view)
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
       Glide.with(context).load(popularData[position].animeImg).into(holder.animeImgView)
        holder.AnimeTitleText.text = popularData[position].animeTitle
        holder.AnimeTitleText.movementMethod = ScrollingMovementMethod.getInstance()
        holder.releaseDateText.text = popularData[position].releasedDate
        holder.AnimeTitleText.setOnClickListener {
            onItemClickText?.invoke(popularData[position])
        }
        holder.animeImgView.setOnClickListener {
            onItemClickText?.invoke(popularData[position])
        }
    }

    override fun getItemCount(): Int {
        return  popularData.size
    }
}