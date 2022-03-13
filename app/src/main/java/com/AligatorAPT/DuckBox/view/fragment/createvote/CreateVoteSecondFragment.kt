package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentCreateVoteSecondBinding
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity
import com.AligatorAPT.DuckBox.view.adapter.createvote.SecondListRVAdapter
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.AligatorAPT.DuckBox.viewmodel.createvote.CVSecondListViewModel
import java.util.*
import kotlin.collections.ArrayList


class CreateVoteSecondFragment: Fragment()  {
    private var _binding : FragmentCreateVoteSecondBinding? = null
    private val binding : FragmentCreateVoteSecondBinding get() = _binding!!
    private val viewModel : CVSecondListViewModel by viewModels()
    var checkValidation = booleanArrayOf(false)
    private var list : ArrayList<String> = arrayListOf()
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
                list.add("")
                viewModel.addTask(list)
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
                viewModel.swapTask(fromPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteTask(viewHolder.layoutPosition)
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
            viewModel.data.observe(viewLifecycleOwner,{
                secondListRVAdapter.setData(it!!)

                val handler = Handler()
                val r = Runnable { secondListRVAdapter.notifyDataSetChanged() }
                handler.post(r)

                Log.e("OBSERVER",it.size.toString()+"내용:"+it.toString())
                checkValidation[0] = it.size>=2
                setIsActivateBtn()
            })
        }
    }


    fun setIsActivateBtn(){
        val mActivity = activity as CreateVoteActivity
        binding.apply {
            if(checkValidation[0]){
                mActivity.binding.createVoteNextTv.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.main))
                mActivity.binding.createVoteNextTv.isEnabled = true
                mActivity.checkValidation[1] = true
            }else{
                mActivity.binding.createVoteNextTv.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.darkgray))
                mActivity.binding.createVoteNextTv.isEnabled = false
                mActivity.checkValidation[1] = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}