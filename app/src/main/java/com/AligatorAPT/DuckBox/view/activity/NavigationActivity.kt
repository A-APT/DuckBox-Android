package com.AligatorAPT.DuckBox.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityNavigationBinding
import com.AligatorAPT.DuckBox.view.fragment.navigation.AlarmFragment
import com.AligatorAPT.DuckBox.view.fragment.navigation.CommunityFragment
import com.AligatorAPT.DuckBox.view.fragment.navigation.HomeFragment
import com.AligatorAPT.DuckBox.view.fragment.navigation.MyFragment

class NavigationActivity : AppCompatActivity() {
    lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        //탭바
        binding.apply {
            bottomNavigation.run {
                setOnItemSelectedListener {
                    when (it.itemId) {
                        R.id.tab_home -> {
                            changeFragment(HomeFragment())
                        }
                        R.id.tab_community -> {
                            changeFragment(CommunityFragment())
                        }
                        R.id.tab_alarm -> {
                            changeFragment(AlarmFragment())
                        }
                        R.id.tab_my -> {
                            changeFragment(MyFragment())
                        }
                    }
                    true
                }
                //초기값 셋팅
                selectedItemId = R.id.tab_home
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.navigationFrameLayout, fragment)
        transaction.commit()
    }
}