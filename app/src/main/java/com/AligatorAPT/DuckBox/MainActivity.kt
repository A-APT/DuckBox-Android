package com.AligatorAPT.DuckBox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.AligatorAPT.DuckBox.databinding.ActivityMainBinding
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity
import com.AligatorAPT.DuckBox.view.activity.LoginActivity
import com.AligatorAPT.DuckBox.view.activity.NavigationActivity
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            //회원가입 화면 전환
            signUpButton.setOnClickListener {
                val intent = Intent(this@MainActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
            //네비게이션 화면 전환
            navigationButton.setOnClickListener {
                val intent = Intent(this@MainActivity, NavigationActivity::class.java)
                startActivity(intent)
            }

            createVoteButton.setOnClickListener {
                val intent = Intent(this@MainActivity, CreateVoteActivity::class.java)
                startActivity(intent)
            }

            LoginButton.setOnClickListener {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
