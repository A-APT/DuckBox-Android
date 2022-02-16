package com.AligatorAPT.DuckBox.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.databinding.RowMyGroupBinding
import com.AligatorAPT.DuckBox.view.data.MyGroupData

class MyGroupAdapter (var items:ArrayList<MyGroupData>)
    : RecyclerView.Adapter<MyGroupAdapter.MyViewHolder>(){

    interface OnItemClickListener{
        fun OnItemClick(holder: MyViewHolder, view: View, data: MyGroupData, position: Int)
    }

    var itemClickListener:OnItemClickListener?= null

    inner class MyViewHolder(val binding: RowMyGroupBinding): RecyclerView.ViewHolder(binding.root) {
        init{
            binding.myGroup.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowMyGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.myGroupImage.setImageResource(items[position].image)
        holder.binding.myGroupName.text = items[position].title
    }
}
