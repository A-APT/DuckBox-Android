package com.AligatorAPT.DuckBox.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.AligatorAPT.DuckBox.databinding.ActivityMyPaperBinding
import com.AligatorAPT.DuckBox.dto.paper.BallotStatus
import com.AligatorAPT.DuckBox.dto.paper.VoteDetailDto
import com.AligatorAPT.DuckBox.view.adapter.PaperListAdapter
import com.google.android.material.tabs.TabLayout
import java.util.*
import kotlin.collections.ArrayList

class MyPaperActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyPaperBinding

    private lateinit var myVoteListAdapter: PaperListAdapter
    private lateinit var mySurveyListAdapter: PaperListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            myPaperBackBtn.setOnClickListener {
                onBackPressed()
            }

            //my vote list 매니저 등록
            myVoteListAdapter = PaperListAdapter(setPaperList())
            myVoteListAdapter.itemClickListener = object : PaperListAdapter.OnItemClickListener {
                override fun OnItemClick(
                    holder: PaperListAdapter.MyViewHolder,
                    view: View,
                    data: VoteDetailDto,
                    time: String,
                    position: Int
                ) {
                    // 투표와 설문이 한꺼번에 받아지는 그날 수정하기
                    if (true) {
                        val intent = Intent(this@MyPaperActivity, VoteDetailActivity::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this@MyPaperActivity, SurveyDetailActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

            //my survey list 매니저 등록
            mySurveyListAdapter = PaperListAdapter(arrayListOf())
            mySurveyListAdapter.itemClickListener = object : PaperListAdapter.OnItemClickListener {
                override fun OnItemClick(
                    holder: PaperListAdapter.MyViewHolder,
                    view: View,
                    data: VoteDetailDto,
                    time: String,
                    position: Int
                ) {
                    // 투표와 설문이 한꺼번에 받아지는 그날 수정하기
                    if (true) {
                        val intent = Intent(this@MyPaperActivity, VoteDetailActivity::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this@MyPaperActivity, SurveyDetailActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

            myPaperTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab!!.position) {
                        0 -> {
                            myPaperRecyclerView.adapter = myVoteListAdapter

                            if(myVoteListAdapter.items.size == 0){
                                emptyMyPaper.visibility = View.VISIBLE
                                myPaperRecyclerView.visibility = View.GONE
                            }else{
                                emptyMyPaper.visibility = View.GONE
                                myPaperRecyclerView.visibility = View.VISIBLE
                            }
                        }
                        1 -> {
                            myPaperRecyclerView.adapter = mySurveyListAdapter

                            if(mySurveyListAdapter.items.size == 0){
                                emptyMyPaper.visibility = View.VISIBLE
                                myPaperRecyclerView.visibility = View.GONE
                            }else{
                                emptyMyPaper.visibility = View.GONE
                                myPaperRecyclerView.visibility = View.VISIBLE
                            }
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })
        }
    }

    private fun setPaperList(): ArrayList<VoteDetailDto> {
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
        return arrayListOf()
    }
}
