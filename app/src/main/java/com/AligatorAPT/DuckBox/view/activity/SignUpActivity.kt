package com.AligatorAPT.DuckBox.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivitySignUpBinding
import com.AligatorAPT.DuckBox.view.fragment.signup.*
import com.AligatorAPT.DuckBox.viewmodel.RegisterViewModel

class SignUpActivity : FragmentActivity() {
    lateinit var binding: ActivitySignUpBinding
    var isTermOfUse = true

    private val model: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        model.setIsNew(true)

        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.signUpFrameLayout, EmailFragment())
        transaction.commit()

        binding.signUpBackBtn.setOnClickListener {
            onBackPressed()
        }
    }

    fun changeFragment(fragment: Fragment, title:String) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.signUpFrameLayout, fragment)
            .addToBackStack(null)
        transaction.commit()
        binding.signUpTitle.text = title
    }
}
