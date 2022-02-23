package com.AligatorAPT.DuckBox.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.AligatorAPT.DuckBox.view.fragment.createvote.CreateVoteFirstFragment
import com.AligatorAPT.DuckBox.view.fragment.createvote.CreateVoteSecondFragment
import com.AligatorAPT.DuckBox.view.fragment.createvote.CreateVoteThirdFragment

class CreateVotePagerAdapter (fragmentActivity : FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CreateVoteFirstFragment()
            1 -> CreateVoteSecondFragment()
            2 -> CreateVoteThirdFragment()
            else -> CreateVoteFirstFragment()
        }
    }
}