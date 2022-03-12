package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.AligatorAPT.DuckBox.databinding.FragmentCreateVoteFinalBinding
import com.AligatorAPT.DuckBox.view.activity.VoteDetailActivity

class CreateVoteFinalFragment: Fragment()  {
    private var _binding : FragmentCreateVoteFinalBinding? = null
    private val binding : FragmentCreateVoteFinalBinding get() = _binding!!

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

        binding.cvFinalCheckTv.setOnClickListener {
            val intent = Intent(activity, VoteDetailActivity::class.java)
            startActivity(intent)
        }
    }

}