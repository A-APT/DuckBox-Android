package com.AligatorAPT.DuckBox.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityChangeInfoBinding
import com.AligatorAPT.DuckBox.view.fragment.signup.MoreInfoFragment
import com.AligatorAPT.DuckBox.viewmodel.RegisterViewModel

class ChangeInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityChangeInfoBinding

    private val model: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        model.setIsNew(false)

        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.changeInfoFrameLayout, MoreInfoFragment())
        transaction.commit()

        binding.apply {
            changeInfoBackBtn.setOnClickListener {
                onBackPressed()
            }
            changeInfoTitle.text = "개인정보변경"
        }
    }
}
