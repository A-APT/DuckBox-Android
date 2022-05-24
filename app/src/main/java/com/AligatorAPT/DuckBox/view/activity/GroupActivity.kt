package com.AligatorAPT.DuckBox.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityGroupBinding
import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto
import com.AligatorAPT.DuckBox.retrofit.callback.SingleGroupCallback
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
        val groupId = intent.getStringExtra("groupId")

        if(groupId == null){
            homeModel!!.myGroup.observe(this, Observer {
                if(it != null){
                    model.setGroupInfo(
                        _name = it[index].name,
                        _profile = it[index].profile,
                        _leader = it[index].leader,
                        _id = it[index].id,
                        _header = it[index].header,
                        _description = it[index].description,
                        _status = it[index].status
                    )
                }
            })
        }else{
            model.findGroupById(groupId, object: SingleGroupCallback{
                override fun apiCallback(flag: Boolean, group: GroupDetailDto?) {
                    if(flag){
                        if (group != null) {
                            model.setGroupInfo(
                                _name = group.name,
                                _profile = group.profile,
                                _leader = group.leader,
                                _id = group.id,
                                _header = group.header,
                                _description = group.description,
                                _status = group.status
                            )
                        }
                    }
                }
            })
        }

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
