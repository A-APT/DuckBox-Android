package com.AligatorAPT.DuckBox.view.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.RowMyGroupBinding
import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto

class MyGroupAdapter (var items:ArrayList<GroupDetailDto>)
    : RecyclerView.Adapter<MyGroupAdapter.MyViewHolder>(){

    interface OnItemClickListener{
        fun OnItemClick(holder: MyViewHolder, view: View, data: GroupDetailDto, position: Int)
    }

    var itemClickListener:OnItemClickListener?= null

    inner class MyViewHolder(val binding: RowMyGroupBinding): RecyclerView.ViewHolder(binding.root) {
        init{
            binding.myGroup.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowMyGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("HOLDER::", items[position].profile!!.encodeToByteArray().toString())

        if(items[position].profile == ""){
            //기본 이미지 설정
            holder.binding.myGroupImage.setImageResource(R.drawable.sub1_color_box_5dp)
        }else{
            val decodedImageBytes: ByteArray = Base64.decode(items[position].profile, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedImageBytes, 0, decodedImageBytes.size)
            holder.binding.myGroupImage.setImageBitmap(bitmap)
        }

        holder.binding.myGroupName.text = items[position].name
    }
}
