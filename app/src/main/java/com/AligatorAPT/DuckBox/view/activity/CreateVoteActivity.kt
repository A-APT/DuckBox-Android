package com.AligatorAPT.DuckBox.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.AligatorAPT.DuckBox.databinding.ActivityCreateVoteBinding
import com.AligatorAPT.DuckBox.databinding.ActivitySignUpBinding
import com.AligatorAPT.DuckBox.view.adapter.CreateVotePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.view.fragment.createvote.CreateVoteFirstFragment
import com.AligatorAPT.DuckBox.view.fragment.createvote.CreateVoteSecondFragment
import com.AligatorAPT.DuckBox.view.fragment.createvote.CreateVoteThirdFragment


class CreateVoteActivity : FragmentActivity() {
    lateinit var binding: ActivityCreateVoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateVoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        val fragment = supportFragmentManager.findFragmentById(R.id.create_vote_fragment)

        Log.e("CreateVOTE",fragment.toString())
        if(fragment == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.create_vote_fragment,  CreateVoteFirstFragment(), )
                .addToBackStack(null)
                .commit()
        }

        binding.createVoteCloseIv.setOnClickListener {
            finish()
        }
    }


//    private fun initViewPager() {
//        val viewPager = binding.createVoteVp
//        viewPager.isUserInputEnabled = false
//        viewPager.adapter = CreateVotePagerAdapter(this)
//        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//
//        val tablayout = binding.createVoteTl
//        TabLayoutMediator(tablayout, viewPager){ tab, position ->
//        }.attach()
//
//        //tablayout에서 tab 간 margin
//        val tabs = tablayout.getChildAt(0) as ViewGroup
//
//        for (i in 0 until tabs.childCount ) {
//            val tab = tabs.getChildAt(i)
//            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
//            layoutParams.weight = 0f
//            layoutParams.marginEnd = 25
//            layoutParams.width = 62
//            tab.layoutParams = layoutParams
//            tablayout.requestLayout()
//        }
//    }
}