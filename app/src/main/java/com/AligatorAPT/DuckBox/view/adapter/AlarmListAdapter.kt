package com.AligatorAPT.DuckBox.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.RowAlarmBinding
import com.AligatorAPT.DuckBox.dto.alarm.AlarmData
import java.util.*
import kotlin.collections.ArrayList

class AlarmListAdapter(var items: ArrayList<AlarmData>) :
    RecyclerView.Adapter<AlarmListAdapter.MyViewHolder>() {

    enum class TimeValue(val value: Int,val maximum : Int, val msg : String) {
        SEC(60,60,"분 전"),
        MIN(60,24,"시간 전"),
        HOUR(24,30,"일 전"),
        DAY(30,12,"달 전"),
        MONTH(12,Int.MAX_VALUE,"년 전")
    }

    interface OnItemClickListener {
        fun OnItemClick(holder: MyViewHolder, view: View, data: AlarmData, position: Int)
        fun OnDeleteClick(holder: MyViewHolder, view: View, position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class MyViewHolder(val binding: RowAlarmBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.alarm.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
            binding.isClose.setOnClickListener {
                itemClickListener?.OnDeleteClick(this, it, adapterPosition)
            }
        }
    }

    fun setData(newData:ArrayList<AlarmData>){
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged()
    }

    fun deleteData(position: Int){
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            alarmGroupName.text = "[${items[position].groupName}]"
            alarmContent.text = items[position].content
            alarmTime.text = timeDiff(items[position].time)

            if(items[position].isNew){
                isNew.visibility = View.VISIBLE
                isClose.visibility = View.GONE
                alarm.setBackgroundResource(R.drawable.sub1_color_box_5dp)
            }else{
                isNew.visibility = View.GONE
                isClose.visibility = View.VISIBLE
                alarm.setBackgroundResource(R.drawable.gray_color_box_5dp)
            }
        }
    }

    private fun timeDiff(date : Date):String{
        val curTime = System.currentTimeMillis()
        var diffTime = (curTime- date.time) / 1000
        var msg = ""
        if(diffTime < TimeValue.SEC.value )
            msg= "방금 전"
        else {
            for (i in TimeValue.values()) {
                diffTime /= i.value
                if (diffTime < i.maximum) {
                    msg= "${diffTime}${i.msg}"
                    break
                }
            }
        }
        return msg
    }
}
