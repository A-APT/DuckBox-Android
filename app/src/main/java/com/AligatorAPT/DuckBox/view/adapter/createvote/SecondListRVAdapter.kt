package com.AligatorAPT.DuckBox.view.adapter.createvote

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.databinding.RowCvSecondTextBinding
import java.util.*
import kotlin.collections.ArrayList

class SecondListRVAdapter(private var items: ArrayList<String?>, val context : Context)
    : RecyclerView.Adapter<SecondListRVAdapter.ViewHolder>(){

    var mList: ArrayList<String?> = arrayListOf()
    val myCustomEditTextListener = MyCustomEditTextListener()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SecondListRVAdapter.ViewHolder {
        val view = RowCvSecondTextBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SecondListRVAdapter.ViewHolder, position: Int) {
            holder.binding.textRvEt.setText("")
            myCustomEditTextListener.updatePosition(holder.adapterPosition)
    }

    override fun getItemCount(): Int = mList.size

    inner class ViewHolder(val binding : RowCvSecondTextBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.apply {
                textRvEt.addTextChangedListener(myCustomEditTextListener)
            }
        }
    }

    inner class MyCustomEditTextListener : TextWatcher {
        private var position:Int = 0

        fun updatePosition(position: Int) {
            this.position = position;
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            mList[position]=p0.toString()
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    fun getList():ArrayList<String?>{
        return mList
    }

    fun clearText(position: Int){
        mList[position]=""
        notifyItemChanged(position)
    }

    fun removeData(position : Int){
        mList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun swapData(fromPos:Int, toPos:Int){
        Collections.swap(mList, fromPos, toPos)
        notifyItemMoved(fromPos, toPos)
    }

    fun addData(){
        mList.add("")
        notifyItemInserted(mList.size-1)
    }

}