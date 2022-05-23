package com.AligatorAPT.DuckBox.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityNavigationBinding
import com.AligatorAPT.DuckBox.view.fragment.navigation.AlarmFragment
import com.AligatorAPT.DuckBox.view.fragment.navigation.CommunityFragment
import com.AligatorAPT.DuckBox.view.fragment.navigation.HomeFragment
import com.AligatorAPT.DuckBox.view.fragment.navigation.MyFragment

class NavigationActivity : AppCompatActivity() {
    lateinit var binding: ActivityNavigationBinding
    private var isAlarm = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if(extras != null)
            isAlarm = extras.getBoolean("isAlarm")
        Log.e("ISALARM", isAlarm.toString())

        init()
    }

    private fun init() {
        binding.apply {
            //탭바
            bottomNavigation.run {
                setOnItemSelectedListener {
                    when (it.itemId) {
                        R.id.tab_home -> {
                            changeFragment(HomeFragment(), "오리상자")
                        }
                        R.id.tab_community -> {
                            changeFragment(CommunityFragment(), "커뮤니티")
                        }
                        R.id.tab_alarm -> {
                            changeFragment(AlarmFragment(), "알림")
                        }
                        R.id.tab_my -> {
                            changeFragment(MyFragment(), "MY")
                        }
                    }
                    true
                }
                //초기값 셋팅
                selectedItemId = R.id.tab_home
            }

            if(isAlarm)
                selectedBottomNavigationItem(R.id.tab_alarm)

            //검색
            searchBtn.setOnClickListener {
                val intent = Intent(this@NavigationActivity, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun selectedBottomNavigationItem(_itemId: Int){
        binding.bottomNavigation.selectedItemId = _itemId
    }

    private fun changeFragment(fragment: Fragment, title: String) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.navigationFrameLayout, fragment)
        transaction.commit()
        binding.apply {
            navigationTitle.text = title
            if (title == "오리상자" || title == "커뮤니티") {
                searchBtn.visibility = View.VISIBLE
            } else {
                searchBtn.visibility = View.INVISIBLE
            }
        }
    }
}
