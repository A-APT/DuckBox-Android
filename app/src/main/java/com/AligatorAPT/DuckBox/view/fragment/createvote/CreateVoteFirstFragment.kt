package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentCreateVoteFirstBinding
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity

class CreateVoteFirstFragment: Fragment()  {
    private var _binding : FragmentCreateVoteFirstBinding? = null
    private val binding : FragmentCreateVoteFirstBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateVoteFirstBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mActivity = activity as CreateVoteActivity

        initIndicator(mActivity)
        binding.cvFirstNextTv.setOnClickListener {
            mActivity.supportFragmentManager.beginTransaction()
                .replace(R.id.create_vote_fragment,  CreateVoteSecondFragment(), )
                .addToBackStack(null)
                .commit()
        }
    }

    private fun initIndicator(mActivity : CreateVoteActivity) {
        mActivity.binding.createVoteIndicatorFirst.setImageResource(R.drawable.vote_indicator_blue)
        mActivity.binding.createVoteIndicatorSecond.setImageResource(R.drawable.vote_indicator)
        mActivity.binding.createVoteIndicatorThird.setImageResource(R.drawable.vote_indicator)
    }
}