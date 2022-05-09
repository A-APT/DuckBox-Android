package com.AligatorAPT.DuckBox.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.AligatorAPT.DuckBox.databinding.ActivityPolicyBinding

class PolicyActivity : AppCompatActivity() {
    lateinit var binding: ActivityPolicyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        val flag = intent.getBooleanExtra("isTerm", true)

        binding.apply {
            policyBackBtn.setOnClickListener {
                onBackPressed()
            }

            if(flag){
                policyTitle.text = "이용약관"
                policyText.text = "이용약관"
            }else{
                policyTitle.text = "개인정보처리방침"
                policyText.text = "개인정보처리방침"
            }
        }
    }
}
