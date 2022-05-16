package com.AligatorAPT.DuckBox.view.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.RowSearchGroupBinding
import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto

class GroupListAdapter (var items:ArrayList<GroupDetailDto>)
    : RecyclerView.Adapter<GroupListAdapter.MyViewHolder>(){

    interface OnItemClickListener{
        fun OnItemClick(holder: MyViewHolder, view: View, data: GroupDetailDto, position: Int)
    }

    var itemClickListener:OnItemClickListener?= null

    inner class MyViewHolder(val binding: RowSearchGroupBinding): RecyclerView.ViewHolder(binding.root) {
        init{
            binding.searchGroup.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    fun setData(newData:ArrayList<GroupDetailDto>){
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowSearchGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(items[position].profile == ""){
            //기본 이미지 설정
            holder.binding.searchGroupImage.setImageResource(R.drawable.user_default)
        }else{
            val decodedImageBytes: ByteArray = Base64.decode(items[position].profile, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedImageBytes, 0, decodedImageBytes.size)
            holder.binding.searchGroupImage.setImageBitmap(bitmap)
        }

        holder.binding.searchGroupName.text = items[position].name
        holder.binding.searchGroupDescription.text = items[position].description
    }
}
