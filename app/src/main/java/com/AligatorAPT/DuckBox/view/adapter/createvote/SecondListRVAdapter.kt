package com.AligatorAPT.DuckBox.view.adapter.createvote

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.databinding.RowCvSecondTextBinding
import com.AligatorAPT.DuckBox.viewmodel.CreateVoteViewModel
import kotlin.collections.ArrayList

class SecondListRVAdapter(val model: CreateVoteViewModel)
    : RecyclerView.Adapter<SecondListRVAdapter.ViewHolder>(){

    var mList : ArrayList<String> = arrayListOf("")

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SecondListRVAdapter.ViewHolder {
        val view = RowCvSecondTextBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SecondListRVAdapter.ViewHolder, position: Int) {
        holder.binding.textRvEt.setText(mList[position])
    }

    override fun getItemCount(): Int = mList.size

    inner class ViewHolder(val binding : RowCvSecondTextBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.apply {
                textRvEt.addTextChangedListener(object: TextWatcher {

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        mList.set(adapterPosition, p0.toString())
                        Log.e("ONTEXT", "position:$adapterPosition  ${model.data.value}")
                        model.setCandidateData(mList)
                    }
                    override fun afterTextChanged(p0: Editable?) {

                    }
                })
            }
        }
    }

    fun addData(){
        mList.add("")
        notifyItemInserted(mList.size)
        Log.e("mList",mList.toString())
    }

    fun swapData(fromPos : Int, toPos: Int){
        val swap = mList[fromPos]
        mList[fromPos] = mList[toPos]
        mList[toPos] = swap
        notifyDataSetChanged()
        Log.e("mList",mList.toString())
    }

    fun removeData(pos: Int){
        mList.removeAt(pos)
        notifyDataSetChanged()
        Log.e("mList",mList.toString())
    }
}