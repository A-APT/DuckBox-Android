package com.AligatorAPT.DuckBox.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityGroupBinding
import com.AligatorAPT.DuckBox.view.fragment.group.GroupDetailFragment

class GroupActivity : AppCompatActivity() {
    lateinit var binding: ActivityGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.groupFrameLayout, GroupDetailFragment())
        transaction.commit()

    }

    fun changeFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.groupFrameLayout, fragment)
            .addToBackStack(null)
        transaction.commit()
    }
}
