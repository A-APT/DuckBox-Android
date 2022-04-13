package com.AligatorAPT.DuckBox.view.adapter.createvote

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.databinding.RowCvFirstImageBinding
import com.bumptech.glide.Glide

class FirstImageRVAdapter(private var items: ArrayList<Bitmap>, val context : Context) : RecyclerView.Adapter<FirstImageRVAdapter.ViewHolder>(){

    interface OnItemClickListener{
        fun OnRemoveClick(holder:ViewHolder, position: Int)
        fun OnAddClick(holder:ViewHolder, position: Int)
    }

    var itemClickListener: OnItemClickListener ?= null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FirstImageRVAdapter.ViewHolder {
        val view = RowCvFirstImageBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FirstImageRVAdapter.ViewHolder, position: Int) {

        holder.binding.apply {
            if(position != items.size-1){
                imageRvIv.setImageBitmap(items[position])
                imageRemoveIv.visibility = View.VISIBLE
                imagePlusIv.visibility = View.GONE
            }else{
                imageRemoveIv.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding : RowCvFirstImageBinding): RecyclerView.ViewHolder(binding.root){
        init{

            binding.apply {
                imageRemoveIv.bringToFront()

                imageRemoveIv.setOnClickListener {
                    itemClickListener?.OnRemoveClick(this@ViewHolder,adapterPosition)
                }
                imageRvIv.setOnClickListener {
                    if(adapterPosition == items.size-1){
                        itemClickListener?.OnAddClick(this@ViewHolder,adapterPosition)
                    }
                }
            }
        }
    }

    fun remove(position : Int){
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}