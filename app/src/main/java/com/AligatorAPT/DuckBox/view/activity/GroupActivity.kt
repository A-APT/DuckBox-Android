package com.AligatorAPT.DuckBox.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityGroupBinding
import com.AligatorAPT.DuckBox.view.fragment.group.GroupDetailFragment
import com.AligatorAPT.DuckBox.viewmodel.GroupViewModel
import com.AligatorAPT.DuckBox.viewmodel.SingletonGroup

class GroupActivity : AppCompatActivity() {
    lateinit var binding: ActivityGroupBinding

    private val model: GroupViewModel by viewModels()
    private val homeModel = SingletonGroup.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        val index = intent.getIntExtra("groupData", 0)

        homeModel!!.myGroup.observe(this, Observer {
            model.setGroupInfo(
                _name = it!![index].name,
                _profile = it!![index].profile,
                _leader = it!![index].leader,
                _id = it!![index].id,
                _header = it!![index].header,
                _description = it!![index].description,
                _status = it!![index].status
            )
        })

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
