package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.fragment.app.activityViewModels
import com.AligatorAPT.DuckBox.viewmodel.CreateVoteViewModel


class CreateVoteSecondFragment: Fragment()  {
    private var _binding : FragmentCreateVoteSecondBinding? = null
    private val binding : FragmentCreateVoteSecondBinding get() = _binding!!
    val viewModel : CreateVoteViewModel by activityViewModels()
    var checkValidation = booleanArrayOf(false)
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
        initList()
        check()
    }


    private fun initList() {
        secondListRVAdapter = SecondListRVAdapter(viewModel)
        binding.apply {
            val layoutManager = LinearLayoutManager(requireContext())
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            cvSecondListRv.layoutManager = layoutManager
            cvSecondListRv.adapter = secondListRVAdapter

            cvSecondAddTv.setOnClickListener {
                secondListRVAdapter.addData()
            }
        }

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                from: RecyclerView.ViewHolder,
                to: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                secondListRVAdapter.removeData(viewHolder.layoutPosition)
                binding.cvSecondListRv[viewHolder.layoutPosition].clearFocus()
            }
        }

        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.cvSecondListRv)

    }

    private fun check() {
        binding.apply {
            viewModel.data.observe(viewLifecycleOwner,{
                Log.e("OBSERVER",it!!.size.toString()+"내용:"+it.toString())
                checkValidation[0] = (it.size>=2 && !it.contains(""))
                if(it.size == 10) {cvSecondAddTv.visibility = View.GONE}
                else {cvSecondAddTv.visibility = View.VISIBLE}
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