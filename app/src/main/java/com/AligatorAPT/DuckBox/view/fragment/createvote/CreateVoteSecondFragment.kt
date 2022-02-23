package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentCreateVoteSecondBinding
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity

class CreateVoteSecondFragment: Fragment()  {
    private var _binding : FragmentCreateVoteSecondBinding? = null
    private val binding : FragmentCreateVoteSecondBinding get() = _binding!!
    private var checkValidation = booleanArrayOf(false)
    private var isActivateBtn = false

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

        val mActivity = activity as CreateVoteActivity



    }


    private fun setIsActivateBtn(){
        val mActivity = activity as CreateVoteActivity
        binding.apply {
            if(checkValidation[0] && checkValidation[1] ){
                mActivity.binding.createVoteNextTv.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.main))
                isActivateBtn = true
            }else{
                mActivity.binding.createVoteNextTv.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.darkgray))
                isActivateBtn = false
            }
        }
    }
}