package com.AligatorAPT.DuckBox.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.RowPaperBinding
import com.AligatorAPT.DuckBox.view.data.PaperListData

class PaperListAdapter(var items: ArrayList<PaperListData>) :
    RecyclerView.Adapter<PaperListAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun OnItemClick(holder: MyViewHolder, view: View, data: PaperListData, position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class MyViewHolder(val binding: RowPaperBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.paper.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    fun notifyChanged() {
        notifyDataSetChanged()
    }

    fun clearData() {
        items.clear()
        notifyDataSetChanged()
    }

    fun getData(): ArrayList<PaperListData> {
        return items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowPaperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            paperListWriter.text = items[position].writer
            paperListImage.setImageResource(items[position].image)
            if (items[position].canParticipate) {
                paperListCanParticipate.text = "참여 가능"
                paperListCanParticipate.setBackgroundResource(R.drawable.sub1_color_box_3dp)
            } else {
                paperListCanParticipate.text = "참여 완료"
                paperListCanParticipate.setBackgroundResource(R.drawable.sub5_color_box_3dp)
            }

            if (items[position].isVote) {
                paperListIsVote.text = "투표"
                paperListIsVote.setBackgroundResource(R.drawable.sub4_color_box_3dp)
            } else {
                paperListIsVote.text = "설문"
                paperListIsVote.setBackgroundResource(R.drawable.sub2_color_box_3dp)
            }
            paperListJoinMember.text = "(${items[position].joinMember}명)"

            paperListRatio.text =
                "${Math.round(items[position].joinMember.toDouble() / items[position].totalMember.toDouble() * 100.0)}% "
            paperListTime.text = items[position].time
            paperListTitle.text = items[position].title
        }
    }
}
