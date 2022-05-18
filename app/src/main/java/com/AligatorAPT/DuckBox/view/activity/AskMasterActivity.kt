package com.AligatorAPT.DuckBox.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.AligatorAPT.DuckBox.databinding.ActivityAskMasterBinding

class AskMasterActivity : AppCompatActivity() {
    lateinit var binding: ActivityAskMasterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAskMasterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.apply {
            askBackBtn.setOnClickListener {
                onBackPressed()
            }

            askBtn.setOnClickListener {
                Toast.makeText(this@AskMasterActivity, "전송되었습니다.", Toast.LENGTH_LONG).show()
                onBackPressed()
            }
        }
    }
}
