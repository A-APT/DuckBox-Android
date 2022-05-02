package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.AligatorAPT.DuckBox.databinding.FragmentCreateVoteFinalBinding
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity
import com.AligatorAPT.DuckBox.view.activity.VoteDetailActivity
import com.AligatorAPT.DuckBox.viewmodel.CreateVoteViewModel

class CreateVoteFinalFragment: Fragment()  {
    private var _binding : FragmentCreateVoteFinalBinding? = null
    private val binding : FragmentCreateVoteFinalBinding get() = _binding!!
    val viewModel : CreateVoteViewModel by viewModels()

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
            val mActivity = activity as CreateVoteActivity
            val intent = Intent(activity, VoteDetailActivity::class.java)
            Log.e("CreateVoteFinal",viewModel.getVoteDto().toString())
            intent.putExtra("vote",viewModel.getVoteDto())
            startActivity(intent)
            mActivity.finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}