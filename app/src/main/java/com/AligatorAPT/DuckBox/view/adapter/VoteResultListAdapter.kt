package com.AligatorAPT.DuckBox.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.databinding.RowVoteResultBinding
import com.AligatorAPT.DuckBox.dto.ethereum.Candidate

class VoteResultListAdapter (
    private var items: ArrayList<Candidate>,
    private var best: Int,
    private var allCount: Int
): RecyclerView.Adapter<VoteResultListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VoteResultListAdapter.ViewHolder {
        val view = RowVoteResultBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: VoteResultListAdapter.ViewHolder, position: Int) {
        holder.binding.apply {
            if(position == best){
                rowResultCrown.visibility = View.VISIBLE
            }else {
                rowResultCrown.visibility = View.GONE
            }
            rowResultText.text = (position+1).toString()+". "+items[position].name
            //수정하기 - 투표 결과 라인
            rowResultBlueline.width
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: RowVoteResultBinding): RecyclerView.ViewHolder(binding.root){
    }

}