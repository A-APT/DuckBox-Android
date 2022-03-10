package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.os.Bundle
import android.util.Log
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

        initButton()

        val mActivity = activity as CreateVoteActivity



    }

    private fun initButton() {
        binding.apply {

            cvSecondTypeRg.setOnCheckedChangeListener { radioGroup, i ->
                if(cvSecondTypeRb1.isChecked){
                    Log.e("RB111","여긴 RB1입니다")
                    cvSecondTypeRb1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.check_blue,0)
                    cvSecondTypeRb2.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
                }
                if(cvSecondTypeRb2.isChecked){
                    Log.e("RB22222","여긴 RB2입니다")
                    cvSecondTypeRb2.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.check_blue,0)
                    cvSecondTypeRb1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
                }
            }
        }
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