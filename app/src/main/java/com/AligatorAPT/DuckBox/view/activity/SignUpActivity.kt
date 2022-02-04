package com.AligatorAPT.DuckBox.view.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivitySignUpBinding
import com.AligatorAPT.DuckBox.view.fragment.SignUp.*

class SignUpActivity : FragmentActivity() {
    lateinit var binding: ActivitySignUpBinding
    var isTermOfUse = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.signUpFrameLayout, EmailFragment())
        transaction.commit()
    }

    fun changeFragment(fragment: Fragment, title:String) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.signUpFrameLayout, fragment)
            .addToBackStack(null)
        transaction.commit()
        binding.signUpTitle.text = title
    }
}
