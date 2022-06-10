package com.AligatorAPT.DuckBox.view.adapter

import android.R.attr
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.databinding.RowVoteResultBinding
import java.math.BigInteger
import android.R.attr.bottom

import android.R.attr.right

import android.R.attr.top

import android.R.attr.left

import android.widget.LinearLayout
import kotlin.math.round


class VoteResultListAdapter(
    private var candidate: ArrayList<String>,
    private var items: List<BigInteger>,
    private var best: Int,
    private var allCount: BigInteger,
    private var deviceWidth: Int,
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
            if(allCount != BigInteger("0") && best != -1){
                if(position == best){
                    rowResultCrown.visibility = View.VISIBLE
                }else {
                    rowResultCrown.visibility = View.GONE
                }
                val percentage: Double = round((items[position].toDouble() / allCount.toDouble()) * 10) / 10
                rowResultText.text = (position+1).toString()+". "+candidate[position] + percentage
                //수정하기 - 투표 결과 라인
                rowResultBlueline.layoutParams.width = (deviceWidth * percentage).toInt()
                rowResultBlueline.requestLayout()

                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.setMargins(0, 0, 0, 40)
                rowResultLayout.layoutParams = lp
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: RowVoteResultBinding): RecyclerView.ViewHolder(binding.root){
    }

}