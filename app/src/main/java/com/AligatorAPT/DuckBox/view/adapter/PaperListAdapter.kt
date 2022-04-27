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
        fun OnItemClick(holder: MyViewHolder, view: View, data: VoteDetailDto, position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class MyViewHolder(val binding: RowPaperBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.paper.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    fun setData(newData:ArrayList<VoteDetailDto>){
        items.clear()
        items.addAll(newData)
        if(!items.isEmpty()){
            Log.e("PAPERLIST_SETDATA",items.toString())
            notifyDataSetChanged()
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
//            if (items[position].canParticipate) {
                paperListCanParticipate.text = "참여 가능"
                paperListCanParticipate.setBackgroundResource(R.drawable.sub1_color_box_3dp)
//            } else {
//                paperListCanParticipate.text = "참여 완료"
//                paperListCanParticipate.setBackgroundResource(R.drawable.sub5_color_box_3dp)
//            }

//            if (items[position].isVote) {
                paperListIsVote.text = "투표"
                paperListIsVote.setBackgroundResource(R.drawable.sub4_color_box_3dp)
//            } else {
//                paperListIsVote.text = "설문"
//                paperListIsVote.setBackgroundResource(R.drawable.sub2_color_box_3dp)
//            }


            compareDate(items[position].startTime,items[position].finishTime)
            paperListTime.text = items[position].finishTime.toString()
            paperListTitle.text = items[position].title
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun compareDate(startTime: Date, finishTime: Date){
        val dateformat = SimpleDateFormat("yyyy,MM,dd,HH,mm,a",Locale.KOREA)
        val dates = dateformat.format(startTime)
        val datef = dateformat.format(finishTime)
        Log.e("PARSEDATE!!!!!!!!!!!!!!!!!!!!",dates.toString()+datef.toString())

        val diff: Long = finishTime.getTime() - startTime.getTime()
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        Log.e("PARSEDATE_SPLIT!!!!!!!!!!!!!!!","min: "+minutes+"hour: "+hours+"days: "+days)

    }
}
