package com.AligatorAPT.DuckBox.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivitySearchBinding
import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto
import com.AligatorAPT.DuckBox.dto.group.GroupStatus
import com.AligatorAPT.DuckBox.view.adapter.GroupListAdapter
import com.AligatorAPT.DuckBox.view.adapter.PaperListAdapter
import com.AligatorAPT.DuckBox.view.data.PaperListData
import com.google.android.material.tabs.TabLayout

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
                communityListAdapter.setData(setPaperList(1))
                groupListAdapter.setData(setGroupList())
            }

            //my paper list 매니저 등록
            myPaperListAdapter = PaperListAdapter(setPaperList(0))
            myPaperListAdapter.itemClickListener = object : PaperListAdapter.OnItemClickListener {
                override fun OnItemClick(
                    holder: PaperListAdapter.MyViewHolder,
                    view: View,
                    data: PaperListData,
                    position: Int
                ) {
                    // 투표 및 설문 상세로 화면 전환
                    if (data.isVote) {
                        val intent = Intent(this@SearchActivity, VoteDetailActivity::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this@SearchActivity, PollDetailActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

            //community paper list 매니저 등록
            communityListAdapter = PaperListAdapter(setPaperList(1))
            communityListAdapter.itemClickListener = object : PaperListAdapter.OnItemClickListener {
                override fun OnItemClick(
                    holder: PaperListAdapter.MyViewHolder,
                    view: View,
                    data: PaperListData,
                    position: Int
                ) {
                    // 투표 및 설문 상세로 화면 전환
                    if (data.isVote) {
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

    private fun setPaperList(flag: Int): ArrayList<PaperListData> {
        when (flag) {
            0 -> {
                return arrayListOf<PaperListData>(
                    PaperListData(
                        R.drawable.sub4_color_box_3dp,
                        "건국대학교 제47회 공과대학 학생회 투표",
                        "KU총학생회",
                        true,
                        true,
                        "3일 06:05:03 남음",
                        100,
                        50
                    ),
                    PaperListData(
                        R.drawable.sub1_color_box_3dp,
                        "건국대학교 제47회 공과대학 학생회 투표",
                        "KU총학생회",
                        true,
                        true,
                        "3일 06:05:03 남음",
                        100,
                        50
                    ),
                    PaperListData(
                        R.drawable.sub2_color_box_3dp,
                        "건국대학교 제47회 공과대학 학생회 투표",
                        "KU총학생회",
                        true,
                        true,
                        "3일 06:05:03 남음",
                        100,
                        50
                    ),
                    PaperListData(
                        R.drawable.sub5_color_box_3dp,
                        "건국대학교 제47회 공과대학 학생회 투표",
                        "KU총학생회",
                        true,
                        true,
                        "3일 06:05:03 남음",
                        100,
                        50
                    ),
                )
            }
            1 -> {
                return arrayListOf<PaperListData>(
                    PaperListData(
                        R.drawable.sub5_color_box_3dp,
                        "남녀사이에 친구가 있다 없다?",
                        "난없다",
                        true,
                        true,
                        "3일 06:05:03 남음",
                        100,
                        50
                    ),
                    PaperListData(
                        R.drawable.sub1_color_box_3dp,
                        "가장 좋아하는 운동은?",
                        "운린이",
                        false,
                        true,
                        "3일 06:05:03 남음",
                        100,
                        50
                    ),
                )
            }
        }
        return arrayListOf()
    }
}
