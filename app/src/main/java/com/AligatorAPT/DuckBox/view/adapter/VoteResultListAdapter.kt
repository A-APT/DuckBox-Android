package com.AligatorAPT.DuckBox.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.databinding.RowVoteResultBinding
import com.AligatorAPT.DuckBox.dto.ethereum.Candidate
import java.math.BigInteger

class VoteResultListAdapter (
    private var candidate: ArrayList<String>,
    private var items: ArrayList<BigInteger>,
    private var best: Int,
    private var allCount: BigInteger
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
            val percentage = items[position] / allCount
            rowResultText.text = (position+1).toString()+". "+candidate[position] + " "+items[position] + " percentage: "+ percentage
            //수정하기 - 투표 결과 라인

        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: RowVoteResultBinding): RecyclerView.ViewHolder(binding.root){
    }

}