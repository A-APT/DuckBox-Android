package com.AligatorAPT.DuckBox.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityVoteDetailBinding

class VoteDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityVoteDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote_detail)
        binding = ActivityVoteDetailBinding.inflate(layoutInflater)
    }
}
