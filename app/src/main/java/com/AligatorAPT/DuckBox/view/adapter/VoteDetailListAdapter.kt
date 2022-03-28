package com.AligatorAPT.DuckBox.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.databinding.RowVdListBinding

class VoteDetailListAdapter(private var items: ArrayList<String>)
    : RecyclerView.Adapter<VoteDetailListAdapter.ViewHolder>(){

    var isChecked: Int = 0

    interface OnItemClickListener{
        fun onTouch(holder: ViewHolder, view: View, position: Int)
    }

    var itemClickListener: OnItemClickListener ?= null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VoteDetailListAdapter.ViewHolder {
        val view = RowVdListBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: VoteDetailListAdapter.ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding : RowVdListBinding): RecyclerView.ViewHolder(binding.root) {
        init{
            binding.apply {
                vdTextTv.text = items[adapterPosition]

                vdTextTv.setOnClickListener {
                    itemClickListener?.onTouch(this@ViewHolder,it,adapterPosition)
                }
            }
        }
    }

    fun Check(position : Int){

    }
}