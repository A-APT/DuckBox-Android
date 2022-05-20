package com.AligatorAPT.DuckBox.view.adapter.createsurvey

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.databinding.RowSurveyMultipleChoiceBinding

class SurveyMultipleRVAdapter(var items: ArrayList<String>)
    : RecyclerView.Adapter<SurveyMultipleRVAdapter.ViewHolder>(){

    interface multipleListener{
        fun onMultipleChange(list: ArrayList<String>)
    }
    var listener: multipleListener ?= null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SurveyMultipleRVAdapter.ViewHolder {
        val view = RowSurveyMultipleChoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SurveyMultipleRVAdapter.ViewHolder, position: Int) {
        holder.binding.multipleEt.setText(items[position])
    }

    override fun getItemCount(): Int  = items.size

    inner class ViewHolder(val binding: RowSurveyMultipleChoiceBinding) : RecyclerView.ViewHolder(binding.root){
        init{
            binding.apply {
                multipleEt.addTextChangedListener(object: TextWatcher{
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        items.set(adapterPosition, p0.toString())
                        listener?.onMultipleChange(items)
                    }

                    override fun afterTextChanged(p0: Editable?) {

                    }
                })
            }
        }
    }

    fun addData(){
        items.add("")
        notifyItemInserted(items.size)
        Log.e("multipleList",items.toString())
    }

    fun removeData(pos: Int){
        items.removeAt(pos)
        notifyDataSetChanged()
        Log.e("multipleList",items.toString())
    }
}