package com.AligatorAPT.DuckBox.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.AligatorAPT.DuckBox.databinding.ActivityCreateVoteBinding
import com.AligatorAPT.DuckBox.view.adapter.createvote.CreateVotePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import android.view.ViewGroup
import android.widget.LinearLayout


class CreateVoteActivity : FragmentActivity() {
    lateinit var binding: ActivityCreateVoteBinding
    lateinit var viewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateVoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
        

        binding.createVoteNextTv.setOnClickListener {
            viewPager.currentItem = viewPager.currentItem+1
            if(viewPager.currentItem == 2) binding.createVoteNextTv.text = "완료"
            else if(viewPager.currentItem == 3){
                binding.createVoteTitle.text = "투표 생성 완료"
                binding.createVoteNextTv.visibility = View.GONE
            }
            else binding.createVoteNextTv.text = "다음"
        }

        binding.createVoteCloseIv.setOnClickListener {
            finish()
        }
    }

    private fun initViewPager() {
        viewPager = binding.createVoteVp
        viewPager.isUserInputEnabled = false
        viewPager.adapter = CreateVotePagerAdapter(this)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val tablayout = binding.createVoteTl
        TabLayoutMediator(tablayout, viewPager){ tab, position ->
            if(position==3){
                binding.createVoteTl.visibility = View.GONE
            }
        }.attach()

        val tabs = tablayout.getChildAt(0) as ViewGroup

        for (i in 0 until tabs.childCount) {
            val tab = tabs.getChildAt(i)
            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 0f
            layoutParams.marginEnd = 25
            layoutParams.width = 62
            tab.layoutParams = layoutParams
            tablayout.requestLayout()
        }
    }

    override fun onBackPressed() {
        val currentItem: Int = viewPager.currentItem
        if(currentItem == 3) finish()
        else if (currentItem != 0) {
            viewPager.setCurrentItem(currentItem - 1, true)
            if(viewPager.currentItem != 2) binding.createVoteNextTv.text = "다음"
        } else{
            Log.e("HERE",currentItem.toString())
            super.onBackPressed()
        }
    }
}