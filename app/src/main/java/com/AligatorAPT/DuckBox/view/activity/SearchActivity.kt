package com.AligatorAPT.DuckBox.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.AligatorAPT.DuckBox.databinding.ActivitySearchBinding
import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto
import com.AligatorAPT.DuckBox.dto.group.GroupStatus
import com.AligatorAPT.DuckBox.view.adapter.GroupListAdapter
import com.AligatorAPT.DuckBox.view.adapter.PaperListAdapter
import com.AligatorAPT.DuckBox.view.data.BallotStatus
import com.AligatorAPT.DuckBox.view.data.VoteDetailDto
import com.google.android.material.tabs.TabLayout
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {
    private lateinit var myPaperListAdapter: PaperListAdapter
    private lateinit var communityListAdapter: PaperListAdapter
    private lateinit var groupListAdapter: GroupListAdapter

    lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            //버튼 이벤트
            searchBack.setOnClickListener {
                onBackPressed()
            }

            searchBtn.setOnClickListener {
                myPaperListAdapter.setData(setPaperList(0))
                communityListAdapter.setData(setPaperList(0))
                groupListAdapter.setData(setGroupList())
            }

            //my paper list 매니저 등록
            myPaperListAdapter = PaperListAdapter(setPaperList(0))
            myPaperListAdapter.itemClickListener = object : PaperListAdapter.OnItemClickListener {
                override fun OnItemClick(
                    holder: PaperListAdapter.MyViewHolder,
                    view: View,
                    data: VoteDetailDto,
                    time: String,
                    position: Int
                ) {
                    // 투표와 설문이 한꺼번에 받아지는 그날 수정하기
                    if (true) {
                        val intent = Intent(this@SearchActivity, VoteDetailActivity::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this@SearchActivity, PollDetailActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

            //community paper list 매니저 등록
            communityListAdapter = PaperListAdapter(setPaperList(0))
            communityListAdapter.itemClickListener = object : PaperListAdapter.OnItemClickListener {
                override fun OnItemClick(
                    holder: PaperListAdapter.MyViewHolder,
                    view: View,
                    data: VoteDetailDto,
                    time: String,
                    position: Int
                ) {
                    // 투표와 설문이 한꺼번에 받아지는 그날 수정하기
                    if (true) {
                        val intent = Intent(this@SearchActivity, VoteDetailActivity::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this@SearchActivity, PollDetailActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

            //group list 매니저 등록
            groupListAdapter = GroupListAdapter(setGroupList())
            groupListAdapter.itemClickListener = object : GroupListAdapter.OnItemClickListener {
                override fun OnItemClick(
                    holder: GroupListAdapter.MyViewHolder,
                    view: View,
                    data: GroupDetailDto,
                    position: Int
                ) {
                    //그룹 상세로 화면 전환
                    val intent = Intent(this@SearchActivity, GroupActivity::class.java)
                    intent.putExtra("groupData", position)
                    startActivity(intent)
                }
            }

            searchTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab!!.position) {
                        0 -> searchRecyclerView.adapter = myPaperListAdapter
                        1 -> searchRecyclerView.adapter = groupListAdapter
                        2 -> searchRecyclerView.adapter = communityListAdapter
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })
        }
    }

    private fun setGroupList(): ArrayList<GroupDetailDto> {
        return arrayListOf<GroupDetailDto>(
            GroupDetailDto(
                "1",
                "ku총학생회",
                "duck",
                GroupStatus.VALID,
                "2022 건국대학교 총학생회입니다.",
                "",
                "",
            ),
            GroupDetailDto(
                "1",
                "ku총학생회",
                "duck",
                GroupStatus.VALID,
                "2022 건국대학교 총학생회입니다.",
                "",
                "",
            ),
        )
    }

    private fun setPaperList(flag: Int): ArrayList<VoteDetailDto> {
        when (flag) {
            0 -> {
                return arrayListOf<VoteDetailDto>(
                    VoteDetailDto(
                        id = "1",
                        title = "건국대학교 제47회 공과대학 학생회 투표",
                        content = "KU총학생회",
                        isGroup = true,
                        groupId = "a",
                        owner = "owner",
                        startTime = Date(),
                        finishTime = Date(),
                        status = BallotStatus.OPEN,
                        images = listOf(),
                        candidates = listOf("a", "b"),
                        voters = null,
                        reward = false
                    ),
                    VoteDetailDto(
                        id = "1",
                        title = "건국대학교 제47회 공과대학 학생회 투표",
                        content = "KU총학생회",
                        isGroup = true,
                        groupId = "a",
                        owner = "owner",
                        startTime = Date(),
                        finishTime = Date(),
                        status = BallotStatus.OPEN,
                        images = listOf(),
                        candidates = listOf("a", "b"),
                        voters = null,
                        reward = false
                    ),
                )
            }
        }
        return arrayListOf()
    }
}
