package com.AligatorAPT.DuckBox.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.RowVotingAndPollsBinding
import com.AligatorAPT.DuckBox.view.data.VoteAndPollListData

class VoteAndPollListAdapter (var items:ArrayList<VoteAndPollListData>)
    : RecyclerView.Adapter<VoteAndPollListAdapter.MyViewHolder>(){

    interface OnItemClickListener{
        fun OnItemClick(holder: MyViewHolder, view: View, data: VoteAndPollListData, position: Int)
    }

    var itemClickListener:OnItemClickListener?= null

    inner class MyViewHolder(val binding: RowVotingAndPollsBinding): RecyclerView.ViewHolder(binding.root) {
        init{
            binding.voteAndPoll.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    fun notifyChanged(){
        notifyDataSetChanged()
    }

    fun clearData(){
        items.clear()
        notifyDataSetChanged()
    }

    fun getData(): ArrayList<VoteAndPollListData>{
        return items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowVotingAndPollsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            voteListGroupName.text = items[position].groupName
            voteListImage.setImageResource(items[position].image)
            if(items[position].canParticipate){
                voteListCanParticipate.text = "참여 가능"
                voteListCanParticipate.setBackgroundResource(R.drawable.sub1_color_box_3dp)
            }else{
                voteListCanParticipate.text = "참여 완료"
                voteListCanParticipate.setBackgroundResource(R.drawable.sub5_color_box_3dp)
            }

            if(items[position].isVote){
                voteListIsVote.text = "투표"
                voteListIsVote.setBackgroundResource(R.drawable.sub4_color_box_3dp)
            }else{
                voteListIsVote.text = "설문"
               voteListIsVote.setBackgroundResource(R.drawable.sub2_color_box_3dp)
            }
            voteListNumOfPeople.text = items[position].numOfPeople
            voteListRatio.text = items[position].ratio
            voteListTime.text = items[position].time
            voteListTitle.text = items[position].title
        }
    }
}
