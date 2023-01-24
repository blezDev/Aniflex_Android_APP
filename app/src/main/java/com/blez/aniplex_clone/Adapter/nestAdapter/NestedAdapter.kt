package com.blez.aniplex_clone.Adapter.nestAdapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blez.aniplex_clone.databinding.RecycerlAdapterBinding

class NestedAdapter(private val context: Context,private val nestedAdapter: NestedAdapter) : RecyclerView.Adapter<NestedAdapter.ViewHolder>(){
    private lateinit var binding : RecycerlAdapterBinding

     inner class ViewHolder(binding : RecycerlAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}