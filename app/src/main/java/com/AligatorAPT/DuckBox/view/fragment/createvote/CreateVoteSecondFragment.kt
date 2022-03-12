package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentCreateVoteSecondBinding
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity
import com.AligatorAPT.DuckBox.view.adapter.createvote.FirstImageRVAdapter
import com.AligatorAPT.DuckBox.view.adapter.createvote.SecondListRVAdapter
import java.util.ArrayList
import android.R.string.no
import android.widget.Toast
import androidx.core.view.get
import androidx.core.widget.doAfterTextChanged


class CreateVoteSecondFragment: Fragment()  {
    private var _binding : FragmentCreateVoteSecondBinding? = null
    private val binding : FragmentCreateVoteSecondBinding get() = _binding!!
    private var checkValidation = booleanArrayOf(true)
    private var isActivateBtn = false
    private var list: ArrayList<String?> = arrayListOf()
    private lateinit var secondListRVAdapter : SecondListRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateVoteSecondBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
        initList()
        check()
    }


    private fun initList() {
        secondListRVAdapter = SecondListRVAdapter(list,requireContext())
        binding.apply {
            val layoutManager = LinearLayoutManager(requireContext())
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            cvSecondListRv.layoutManager = layoutManager
            cvSecondListRv.adapter = secondListRVAdapter

            cvSecondAddTv.setOnClickListener {
                secondListRVAdapter.addData()
            }
        }

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback (
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                from: RecyclerView.ViewHolder,
                to: RecyclerView.ViewHolder
            ): Boolean {
                val fromPos: Int = from.adapterPosition
                val toPos: Int = to.adapterPosition
                secondListRVAdapter.swapData(fromPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                secondListRVAdapter.clearText(viewHolder.layoutPosition)
                secondListRVAdapter.removeData(viewHolder.layoutPosition)
                binding.cvSecondListRv.get(viewHolder.layoutPosition).clearFocus()
            }
        }

        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.cvSecondListRv)

    }

    private fun initButton() {
        binding.apply {

            cvSecondTypeRg.setOnCheckedChangeListener { radioGroup, i ->
                if(cvSecondTypeRb1.isChecked){
                    cvSecondTypeRb1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.check_blue,0)
                    cvSecondTypeRb2.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
                }
                if(cvSecondTypeRb2.isChecked){
                    cvSecondTypeRb2.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.check_blue,0)
                    cvSecondTypeRb1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
                }
            }
        }
    }

    private fun check() {

        binding.apply {

            val list = secondListRVAdapter.getList()

            var final_list : ArrayList<String> = arrayListOf()

            for(i in 0 until list.size-1){
                if(list[i]!="") final_list.add(list[i]!!)
            }

//            checkValidation[0] = final_list.size >= 2
//            setIsActivateBtn()

        }
    }


    private fun setIsActivateBtn(){
        val mActivity = activity as CreateVoteActivity
        binding.apply {
            if(checkValidation[0]){
                mActivity.binding.createVoteNextTv.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.main))
                isActivateBtn = true
            }else{
                mActivity.binding.createVoteNextTv.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.darkgray))
                isActivateBtn = false
            }
        }
    }
}