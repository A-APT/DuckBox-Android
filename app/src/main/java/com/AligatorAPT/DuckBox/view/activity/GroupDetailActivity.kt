package com.AligatorAPT.DuckBox.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityGroupDetailBinding
import com.AligatorAPT.DuckBox.view.adapter.PaperListAdapter
import com.AligatorAPT.DuckBox.view.data.MyGroupData
import com.AligatorAPT.DuckBox.view.data.PaperListData
import com.AligatorAPT.DuckBox.view.dialog.ModalDialog
import com.google.android.material.tabs.TabLayout
import java.text.DecimalFormat

class GroupDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityGroupDetailBinding

    private var _groupDescription = "2022 건국대학교 총학생회입니다."
    private var _groupMembers = 2752
    private var isGroupMember = true

    lateinit var groupData:MyGroupData

    private lateinit var paperListAdapter: PaperListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        groupData = intent.getSerializableExtra("groupData") as MyGroupData
        val decimal = DecimalFormat("###,###,###")
        binding.apply {
            //그룹 정보 추가 (나중에 서버에서 받아올 정보는 추후 수정)
            groupTitle.text = groupData.title
            groupDescription.text = _groupDescription
            groupMembers.text = "그룹원 ${decimal.format(_groupMembers)}"

            //그룹 가입 여부
            if(isGroupMember){
                joinGroup.visibility = View.GONE
                mutualAuthentication.visibility = View.VISIBLE
            }else{
                joinGroup.visibility = View.VISIBLE
                mutualAuthentication.visibility = View.GONE
            }

            //버튼 이벤트
            mutualAuthentication.setOnClickListener {
                if(isGroupMember){
                    val intent = Intent(this@GroupDetailActivity, MutualAuthActivity::class.java)
                    intent.putExtra("groupName", groupData.title)
                    intent.putExtra("groupDescription", _groupDescription)
                    startActivity(intent)
                }
            }

            joinGroup.setOnClickListener {
                if(!isGroupMember){
                    //다이얼로그
                    val bundle = Bundle()
                    bundle.putString("message", "그룹에 가입하시겠습니까?")
                    val modalDialog = ModalDialog()
                    modalDialog.arguments = bundle
                    modalDialog.itemClickListener = object : ModalDialog.OnItemClickListener{
                        override fun OnPositiveClick() {
                            modalDialog.dismiss()
                            //그룹 가입 요청 완료로 화면 전환
                            val intent = Intent(this@GroupDetailActivity, ResultActivity::class.java)
                            intent.putExtra("isType", 0)
                            intent.putExtra("groupName", groupData.title)
                            startActivity(intent)
                        }

                        override fun OnNegativeClick() {
                            modalDialog.dismiss()
                        }
                    }
                    modalDialog.show(this@GroupDetailActivity.supportFragmentManager, "ModalDialog")
                }
            }

            backBtn.setOnClickListener {
                onBackPressed()
            }

            groupSetting.setOnClickListener {
                //그룹 설정로 화면 전환
                val intent = Intent(this@GroupDetailActivity, GroupSettingActivity::class.java)
                intent.putExtra("groupData", groupData)
                startActivity(intent)
            }

            //pager list 매니저 등록
            paperListAdapter = PaperListAdapter(setPaperList(0))
            paperListAdapter.itemClickListener = object :PaperListAdapter.OnItemClickListener{
                override fun OnItemClick(
                    holder: PaperListAdapter.MyViewHolder,
                    view: View,
                    data: PaperListData,
                    position: Int
                ) {
                    if(isGroupMember){
                        // 투표 및 설문 상세로 화면 전환
                        if(data.isVote){
                            val intent = Intent(this@GroupDetailActivity, VoteDetailActivity::class.java)
                            startActivity(intent)
                        }else{
                            val intent = Intent(this@GroupDetailActivity, PollDetailActivity::class.java)
                            startActivity(intent)
                        }
                    }else{
                        //다이얼로그
                        val bundle = Bundle()
                        bundle.putString("message", "그룹원만 열람할 수 있습니다.\n" +
                                "그룹에 가입해주세요.")
                        val modalDialog = ModalDialog()
                        modalDialog.arguments = bundle
                        modalDialog.itemClickListener = object : ModalDialog.OnItemClickListener{
                            override fun OnPositiveClick() {
                                modalDialog.dismiss()
                            }

                            override fun OnNegativeClick() {
                                modalDialog.dismiss()
                            }
                        }
                        modalDialog.show(this@GroupDetailActivity.supportFragmentManager, "ModalDialog")
                    }
                }
            }
            recyclerView.adapter = paperListAdapter

            groupTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    paperListAdapter.setData(setPaperList(tab!!.position))
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })
        }
    }

    private fun setPaperList(flag: Int): ArrayList<PaperListData>{
        when(flag){
            0->{
                return arrayListOf<PaperListData>(
                    PaperListData(R.drawable.sub4_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", 100, 50),
                    PaperListData(R.drawable.sub1_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", 100, 50),
                    PaperListData(R.drawable.sub2_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", 100, 50),
                    PaperListData(R.drawable.sub5_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", 100, 50),
                )
            }
            1->{
                return arrayListOf<PaperListData>(
                    PaperListData(R.drawable.sub5_color_box_3dp, "건국대학교 중간고사 간식어택", "KU총학생회", false, true, "3일 06:05:03 남음", 100, 50),
                    PaperListData(R.drawable.sub1_color_box_3dp, "건국대학교 중간고사 멘토멘티", "KU총학생회", false, true, "3일 06:05:03 남음", 100, 50),
                )
            }
            2->{
                return arrayListOf<PaperListData>(
                    PaperListData(R.drawable.sub5_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, false, "3일 06:05:03 남음", 100, 50),
                    PaperListData(R.drawable.sub1_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, false, "3일 06:05:03 남음", 100, 50),
                )
            }
        }
        return arrayListOf()
    }
}
