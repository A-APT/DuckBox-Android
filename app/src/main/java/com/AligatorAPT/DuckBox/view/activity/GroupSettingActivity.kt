package com.AligatorAPT.DuckBox.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.AligatorAPT.DuckBox.databinding.ActivityGroupSettingBinding
import com.AligatorAPT.DuckBox.view.data.MyGroupData
import com.AligatorAPT.DuckBox.view.dialog.ModalDialog
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
                if(isGroupMaster){
                    //다이얼로그
                    val bundle = Bundle()
                    bundle.putString("message", "그룹을 삭제하시겠습니까?\n그룹 삭제 시 게시글과 참여 내역은\n자동으로 삭제되지 않습니다.")
                    val modalDialog = ModalDialog()
                    modalDialog.arguments = bundle
                    modalDialog.itemClickListener = object : ModalDialog.OnItemClickListener{
                        override fun OnPositiveClick() {
                            modalDialog.dismiss()
                            //그룹 삭제 완료로 화면 전환
                            val intent = Intent(this@GroupSettingActivity, ResultActivity::class.java)
                            intent.putExtra("isType", 2)
                            startActivity(intent)
                        }

                        override fun OnNegativeClick() {
                            modalDialog.dismiss()
                        }
                    }
                    modalDialog.show(this@GroupSettingActivity.supportFragmentManager, "ModalDialog")
                }
            }
            outGroup.setOnClickListener {
                if(isGroupMember){
                    //다이얼로그
                    val bundle = Bundle()
                    bundle.putString("message", "그룹에서 탈퇴하시겠습니까?\n탈퇴 시 작성된 게시글과 참여내역은\n자동으로 삭제되지 않습니다.")
                    val modalDialog = ModalDialog()
                    modalDialog.arguments = bundle
                    modalDialog.itemClickListener = object : ModalDialog.OnItemClickListener{
                        override fun OnPositiveClick() {
                            modalDialog.dismiss()
                            //그룹 탈퇴 완료로 화면 전환
                            val intent = Intent(this@GroupSettingActivity, ResultActivity::class.java)
                            intent.putExtra("isType", 1)
                            startActivity(intent)
                        }

                        override fun OnNegativeClick() {
                            modalDialog.dismiss()
                        }
                    }
                    modalDialog.show(this@GroupSettingActivity.supportFragmentManager, "ModalDialog")
                }
            }
            reportGroup.setOnClickListener {

            }
            backBtn.setOnClickListener {
                onBackPressed()
            }
        }
    }
}
