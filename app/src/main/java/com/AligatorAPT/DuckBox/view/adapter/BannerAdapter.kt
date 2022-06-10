package com.AligatorAPT.DuckBox.view.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.databinding.RowBannerBinding

class BannerAdapter (var items:ArrayList<String>)
    : RecyclerView.Adapter<BannerAdapter.MyViewHolder>(){

    interface OnItemClickListener{
        fun OnItemClick(data: String, selected_position: Int)
    }

    var itemClickListener:OnItemClickListener?= null

    inner class MyViewHolder(val binding: RowBannerBinding): RecyclerView.ViewHolder(binding.root) {
        init{
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
        val decodedImageBytes: ByteArray = Base64.decode(items[position], Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedImageBytes, 0, decodedImageBytes.size)
        holder.binding.bannerImage.setImageBitmap(bitmap)
        holder.binding.bannerImage.scaleType = ImageView.ScaleType.FIT_CENTER
        holder.binding.banner.setOnClickListener {
            itemClickListener?.OnItemClick(items[position], position)
        }
    }
}
