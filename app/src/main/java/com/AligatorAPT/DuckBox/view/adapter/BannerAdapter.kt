package com.AligatorAPT.DuckBox.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.databinding.RowBannerBinding

class BannerAdapter (var items:ArrayList<Int>)
    : RecyclerView.Adapter<BannerAdapter.MyViewHolder>(){

    interface OnItemClickListener{
        fun OnItemClick(holder: MyViewHolder, view: View, data: Int, position: Int)
    }

    var itemClickListener:OnItemClickListener?= null

    inner class MyViewHolder(val binding: RowBannerBinding): RecyclerView.ViewHolder(binding.root) {
        init{
            binding.banner.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.bannerImage.setImageResource(items[position])
    }
}
