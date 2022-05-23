package com.AligatorAPT.DuckBox.view.adapter.createsurvey

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.AligatorAPT.DuckBox.view.fragment.createsurvey.CreateSurveySecondFragment
import com.AligatorAPT.DuckBox.view.fragment.createvote.CreateVoteFirstFragment
import com.AligatorAPT.DuckBox.view.fragment.createvote.CreateVoteSecondFragment
import com.AligatorAPT.DuckBox.view.fragment.createvote.CreateVoteThirdFragment

class CreateSurveyPagerAdapter (fragmentActivity : FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CreateVoteFirstFragment()
            1 -> CreateSurveySecondFragment()
            2 -> CreateVoteThirdFragment()
            else -> CreateVoteFirstFragment()
        }
    }
}