package com.blez.aniplex_clone.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.R

class LoaderAdapter : LoadStateAdapter<LoaderAdapter.LoadViewHolder>(){
    class LoadViewHolder(itemview : View): RecyclerView.ViewHolder(itemview){
        val progressBar = itemview.findViewById<ProgressBar>(R.id.progressBar)

        fun bind(loadState : LoadState){
            progressBar.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
       holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.loader_item,parent,false)
        return LoadViewHolder(view)
    }

}