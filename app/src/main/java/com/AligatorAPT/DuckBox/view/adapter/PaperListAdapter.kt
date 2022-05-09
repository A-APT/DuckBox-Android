package com.AligatorAPT.DuckBox.view.adapter

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.util.Base64
import android.util.Log
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.RowPaperBinding
import com.AligatorAPT.DuckBox.view.data.VoteDetailDto
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PaperListAdapter(var items: ArrayList<VoteDetailDto>) :
    RecyclerView.Adapter<PaperListAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun OnItemClick(holder: MyViewHolder, view: View, data: VoteDetailDto, time: String, position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class MyViewHolder(val binding: RowPaperBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.paper.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], compareDate(items[position].startTime,items[position].finishTime), adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowPaperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PaperListAdapter.MyViewHolder, position: Int) {
        holder.binding.apply {
            Log.e("PAPERLISTADPATER",items[position].toString())
            paperListWriter.text = items[position].owner

            val decodedImageBytes = Base64.decode(items[position].images[0], Base64.DEFAULT);
            val bitmap = BitmapFactory.decodeByteArray(decodedImageBytes, 0, decodedImageBytes.size)
            holder.binding.paperListImage.setImageBitmap(bitmap)

            paperListCanParticipate.text = "참여 가능"
            paperListCanParticipate.setBackgroundResource(R.drawable.sub1_color_box_3dp)
            paperListIsVote.text = "투표"
            paperListIsVote.setBackgroundResource(R.drawable.sub4_color_box_3dp)

            paperListTime.text = compareDate(items[position].startTime,items[position].finishTime)
            paperListTitle.text = items[position].title
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun compareDate(startTime: Date, finishTime: Date): String{

        var text = ""

        val diff: Long = finishTime.getTime() - startTime.getTime()
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val years = days / 365

        if(years != 0L){
            text += "${years}년"
        }
        if(days-(365*years) != 0L){
            text += "${days-(365*years)}일"
        }
        if(hours-(24*days) != 0L){
            text += "${hours-(24*days)}시간"
        }
        if(minutes-(60*hours) != 0L) {
            text += "${minutes-(60*hours)}분"
        }
        text += " 남음"
        Log.e("PARSEDATE_SPLIT!!!!!!!!!!!!!!!","min: "+minutes+"hour: "+hours+"days: "+days)

        return text
    }
}
