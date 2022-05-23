package com.AligatorAPT.DuckBox.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivitySurveyDetailBinding
import com.AligatorAPT.DuckBox.databinding.ActivityVoteDetailBinding

class SurveyDetailActivity: AppCompatActivity() {
    lateinit var binding : ActivitySurveyDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote_detail)
        binding = ActivitySurveyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}