package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.AligatorAPT.DuckBox.databinding.FragmentCreateVoteFinalBinding
import com.AligatorAPT.DuckBox.view.activity.CreateSurveyActivity
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity

class CreateVoteFinalFragment: Fragment()  {
    private var _binding : FragmentCreateVoteFinalBinding? = null
    private val binding : FragmentCreateVoteFinalBinding get() = _binding!!
    private var isVote = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateVoteFinalBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments != null){
            isVote = arguments!!.getBoolean("isVote")
        }

        binding.cvFinalCheckTv.setOnClickListener {
            if(isVote){
                val mActivity = activity as CreateVoteActivity
                mActivity.finish()
            }else{
                val mActivity = activity as CreateSurveyActivity
                mActivity.finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}