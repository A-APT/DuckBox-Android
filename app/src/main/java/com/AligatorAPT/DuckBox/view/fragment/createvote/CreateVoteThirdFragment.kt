package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import com.AligatorAPT.DuckBox.databinding.FragmentCreateVoteThirdBinding
import android.content.Context
import com.AligatorAPT.DuckBox.R


class CreateVoteThirdFragment: Fragment()  {
    private var _binding : FragmentCreateVoteThirdBinding? = null
    private val binding : FragmentCreateVoteThirdBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateVoteThirdBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
    }

    private fun initButton() {
        binding.apply {
            val folder_black = getDrawable(requireContext(), R.drawable.folder_black)!!.constantState
            val bell_black = getDrawable(requireContext(), R.drawable.bell_black)!!.constantState
            val gray_box = getDrawable(requireContext(), R.drawable.gray_color_box_5dp)!!.constantState
            val reward_black = getDrawable(requireContext(), R.drawable.reward_black)!!.constantState

            cvThirdListTv.setOnClickListener {
                if(cvThirdListIv.drawable.constantState!! == folder_black){
                    cvThirdListIv.setImageResource(R.drawable.folder_blue)
                    cvThirdListTitleTv.visibility = View.VISIBLE
                    cvThirdListTv.setBackgroundResource(R.drawable.main_stroke_sub1_solid_box_5dp)
                    cvThirdListTv.setTextColor(resources.getColor(R.color.main,null))
                }else{
                    cvThirdListIv.setImageResource(R.drawable.folder_black)
                    cvThirdListTitleTv.visibility = View.GONE
                    cvThirdListTv.setBackgroundResource(R.drawable.gray_color_box_5dp)
                    cvThirdListTv.setTextColor(resources.getColor(R.color.black,null))
                }
            }
            cvThirdAlarmTv.setOnClickListener {
                if(cvThirdAlarmIv.drawable.constantState!! == bell_black){
                    cvThirdAlarmIv.setImageResource(R.drawable.bell_blue)
                    cvThirdAlarmTv.setBackgroundResource(R.drawable.main_stroke_sub1_solid_box_5dp)
                    cvThirdAlarmTv.setTextColor(resources.getColor(R.color.main,null))
                }else{
                    cvThirdAlarmIv.setImageResource(R.drawable.bell_black)
                    cvThirdAlarmTv.setBackgroundResource(R.drawable.gray_color_box_5dp)
                    cvThirdAlarmTv.setTextColor(resources.getColor(R.color.black,null))
                }
            }
            cvThirdRewardTv.setOnClickListener {
                if(cvThirdRewardIv.drawable.constantState == reward_black) {
                    cvThirdRewardIv.setImageResource(R.drawable.reward_blue)
                    cvThirdRewardTv.setBackgroundResource(R.drawable.main_stroke_sub1_solid_box_5dp)
                    cvThirdRewardTv.setTextColor(resources.getColor(R.color.main,null))
                }else{
                    cvThirdRewardIv.setImageResource(R.drawable.reward_black)
                    cvThirdRewardTv.setBackgroundResource(R.drawable.gray_color_box_5dp)
                    cvThirdRewardTv.setTextColor(resources.getColor(R.color.black,null))
                }
            }
        }
    }

}