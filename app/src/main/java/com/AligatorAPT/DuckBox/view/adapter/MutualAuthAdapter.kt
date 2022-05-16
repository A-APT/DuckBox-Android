package com.AligatorAPT.DuckBox.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.databinding.RowMutualAuthBinding
import com.AligatorAPT.DuckBox.view.data.MutualAuthData

class MutualAuthAdapter(var items: ArrayList<MutualAuthData>):
    RecyclerView.Adapter<MutualAuthAdapter.MyViewHolder>() {
    interface OnItemClickListener {
        fun OnItemClick(holder: MyViewHolder, view: View, data: MutualAuthData, position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class MyViewHolder(val binding: RowMutualAuthBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.approveBtn.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    fun deleteData(_index: Int){
        items.removeAt(_index)
        notifyItemRemoved(_index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowMutualAuthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            userName.text = items[position].name
            userInfo.text = items[position].email
        }
    }
}
