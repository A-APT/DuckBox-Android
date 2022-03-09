package com.AligatorAPT.DuckBox.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.AligatorAPT.DuckBox.databinding.ActivityGroupSettingBinding
import com.AligatorAPT.DuckBox.view.data.MyGroupData
import java.text.DecimalFormat

class GroupSettingActivity : AppCompatActivity() {
    lateinit var binding: ActivityGroupSettingBinding

    private var _groupDescription = "2022 건국대학교 총학생회입니다."
    private var _groupMembers = 2752
    private var _groupValid = true

    private var isGroupMember = false
    private var isGroupMaster = true
    lateinit var groupData: MyGroupData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        groupData = intent.getSerializableExtra("groupData") as MyGroupData
        val decimal = DecimalFormat("###,###,###")

        binding.apply {
            //그룹 정보 추가 (추후 서버 연동에 따라 변경 가능)
            groupTitle.text = groupData.title
            groupDescription.text = _groupDescription
            groupMembers.text = "${decimal.format(_groupMembers)} 명"
            if(_groupValid)
                groupValid.text = "완료"
            else
                groupValid.text = "대기중"
            groupImage.setImageResource(groupData.image)

            //그룹장, 그룹원 여부
            if(isGroupMaster){
                updateGroup.visibility = View.VISIBLE
                deleteGroup.visibility = View.VISIBLE
                membersBtn.visibility = View.VISIBLE
                outGroup.visibility = View.GONE
                reportGroup.visibility = View.GONE
            }else if(isGroupMember){
                updateGroup.visibility = View.GONE
                deleteGroup.visibility = View.GONE
                membersBtn.visibility = View.VISIBLE
                outGroup.visibility = View.VISIBLE
                reportGroup.visibility = View.VISIBLE
            }else{
                updateGroup.visibility = View.GONE
                deleteGroup.visibility = View.GONE
                membersBtn.visibility = View.GONE
                outGroup.visibility = View.GONE
                reportGroup.visibility = View.VISIBLE
            }

            //버튼 이벤트
            updateGroup.setOnClickListener {
                val intent = Intent(this@GroupSettingActivity, GroupUpdateActivity::class.java)
                intent.putExtra("groupData", groupData)
                startActivity(intent)
            }
            membersBtn.setOnClickListener {

            }
            deleteGroup.setOnClickListener {

            }
            outGroup.setOnClickListener {

            }
            reportGroup.setOnClickListener {

            }
            backBtn.setOnClickListener {
                onBackPressed()
            }
        }
    }
}
