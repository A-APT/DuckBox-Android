package com.AligatorAPT.DuckBox.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityCreateGroupBinding
import com.AligatorAPT.DuckBox.view.fragment.creategroup.CreateGroupInfoFragment

class CreateGroupActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreateGroupBinding
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.createGroupFrameLayout, CreateGroupInfoFragment())
        transaction.commit()

        binding.createGroupBackBtn.setOnClickListener {
            onBackPressed()
        }
    }

    fun changeFragment(fragment: Fragment, title:String, _page: Int) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.createGroupFrameLayout, fragment)
            .addToBackStack(null)
        transaction.commit()
        binding.createGroupTitle.text = title
        page = _page
    }

    override fun onBackPressed() {
        super.onBackPressed()
        when(page){
            0 -> {
                finish()
            }
            1 -> {
                binding.createGroupTitle.text = "그룹 정보 입력하기"
                page = 0
            }
            2 -> {
                finish()
            }
        }
    }
}
