package com.AligatorAPT.DuckBox.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityGroupBinding
import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto
import com.AligatorAPT.DuckBox.view.fragment.group.GroupDetailFragment
import com.AligatorAPT.DuckBox.viewmodel.GroupViewModel

class GroupActivity : AppCompatActivity() {
    lateinit var binding: ActivityGroupBinding

    private val model: GroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        val groupInfo = intent.getSerializableExtra("groupData") as GroupDetailDto
        model.setGroupInfo(
            _name = groupInfo.name,
            _profile = groupInfo.profile,
            _leader = groupInfo.leader,
            _id = groupInfo.id,
            _header = groupInfo.header,
            _description = groupInfo.description,
            _status = groupInfo.status
        )

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
