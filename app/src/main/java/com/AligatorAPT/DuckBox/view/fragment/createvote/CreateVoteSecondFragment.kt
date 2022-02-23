package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentCreateVoteSecondBinding
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity

class CreateVoteSecondFragment: Fragment()  {
    private var _binding : FragmentCreateVoteSecondBinding? = null
    private val binding : FragmentCreateVoteSecondBinding get() = _binding!!

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

        initIndicator(mActivity)
        binding.cvSecondNextTv.setOnClickListener {
            mActivity.supportFragmentManager.beginTransaction()
                .replace(R.id.create_vote_fragment,  CreateVoteThirdFragment(), )
                .addToBackStack(null)
                .commit()
        }


    }

    private fun initIndicator(mActivity : CreateVoteActivity) {
        mActivity.binding.createVoteIndicatorFirst.setImageResource(R.drawable.vote_indicator)
        mActivity.binding.createVoteIndicatorSecond.setImageResource(R.drawable.vote_indicator_blue)
        mActivity.binding.createVoteIndicatorThird.setImageResource(R.drawable.vote_indicator)
    }
}