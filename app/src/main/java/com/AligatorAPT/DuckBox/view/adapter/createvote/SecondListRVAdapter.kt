package com.AligatorAPT.DuckBox.view.adapter.createvote

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.databinding.RowCvSecondTextBinding
import com.AligatorAPT.DuckBox.view.fragment.createvote.CreateVoteSecondFragment
import com.AligatorAPT.DuckBox.viewmodel.createvote.CVSecondListViewModel
import java.util.*
import kotlin.collections.ArrayList

class SecondListRVAdapter(private var items: ArrayList<String>, val context: Context)
    : RecyclerView.Adapter<SecondListRVAdapter.ViewHolder>(){

    var mList : ArrayList<String> = arrayListOf()

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
                        Log.e("ONTEXT", "position:$adapterPosition  $items")
                    }
                    override fun afterTextChanged(p0: Editable?) {

                    }
                })
            }
        }
    }

    fun setData(newData: ArrayList<String>){
        mList = newData
    }
}