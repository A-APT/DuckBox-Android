package com.AligatorAPT.DuckBox.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.RowVdListBinding
import com.AligatorAPT.DuckBox.viewmodel.VoteDetailViewModel
import kotlin.properties.Delegates

class VoteDetailListAdapter(
    private var items: ArrayList<String>,
    private var model: VoteDetailViewModel
)
    : RecyclerView.Adapter<VoteDetailListAdapter.ViewHolder>(){

    var isClickable : Boolean = true

    var selectedPosition by Delegates.observable(-1) { property, oldPos, newPos ->
        if(isClickable && newPos in items.indices){
            notifyItemChanged(oldPos)
            notifyItemChanged(newPos)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = RowVdListBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(isClickable && position in items.indices){
            holder.bind(items[position], position == selectedPosition)
            holder.binding.vdTextTv.setOnClickListener { selectedPosition = position }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding : RowVdListBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String, selected: Boolean){
            binding.apply {
                vdTextTv.text = item
                if(selected) {
                    model.setIsSelected(true)
                    vdCheckIv.visibility = View.VISIBLE
                    vdTextCv.setBackgroundResource(R.drawable.main_stroke_sub1_solid_box_5dp)
                }
                else {
                    vdCheckIv.visibility = View.GONE
                    vdTextCv.setBackgroundResource(R.drawable.white_color_box_5dp)
                }
            }
        }
    }
}