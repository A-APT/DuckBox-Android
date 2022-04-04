package com.AligatorAPT.DuckBox.view.adapter.votedetail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.databinding.ItemVotedetailImageBinding
import java.util.*

class VoteDetailImageAdapter(private var items: ArrayList<Int>)
    : RecyclerView.Adapter<VoteDetailImageAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun OnClick(position: Int)
    }
    var itemClickListener: OnItemClickListener?= null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = ItemVotedetailImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: VoteDetailImageAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.binding.itemVdIv.setOnClickListener {
            itemClickListener?.OnClick(position)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: ItemVotedetailImageBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Int){
            binding.itemVdIv.setImageResource(item)
        }
    }
}