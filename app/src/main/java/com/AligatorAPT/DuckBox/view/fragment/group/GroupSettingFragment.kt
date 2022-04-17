package com.AligatorAPT.DuckBox.view.fragment.group

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentGroupSettingBinding
import com.AligatorAPT.DuckBox.dto.group.GroupStatus
import com.AligatorAPT.DuckBox.view.activity.GroupActivity
import com.AligatorAPT.DuckBox.view.activity.ReportActivity
import com.AligatorAPT.DuckBox.view.activity.ResultActivity
import com.AligatorAPT.DuckBox.view.dialog.ModalDialog
import com.AligatorAPT.DuckBox.viewmodel.GroupViewModel

class GroupSettingFragment : Fragment() {
    private var _binding: FragmentGroupSettingBinding? = null
    private val binding: FragmentGroupSettingBinding get() = _binding!!

    private val model: GroupViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init(){
        val mActivity = activity as GroupActivity

        binding.apply {
            //그룹 정보 추가
            model.name.observe(viewLifecycleOwner, Observer {
                groupTitle.text = it
            })
            model.description.observe(viewLifecycleOwner, Observer {
                groupDescription.text = it
            })

            model.status.observe(viewLifecycleOwner, Observer {
                if(it == GroupStatus.PENDING)
                    groupValid.text = "대기중"
                else
                    groupValid.text = "완료"
            })

            //이미지
            model.header.observe(viewLifecycleOwner, Observer {
                if(it == null){
                    groupBackground.setImageResource(R.drawable.sub1_color_box_5dp)
                }else{
                    val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                    groupBackground.setImageBitmap(bmp)
                }
            })

            model.profile.observe(viewLifecycleOwner, Observer {
                if(it != null){
                    val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                    groupImage.setImageBitmap(bmp)
                }else{
                    groupImage.setImageResource(R.drawable.sub2_color_box_3dp)
                }
            })

            //그룹장, 그룹원 여부
            model.authority.observe(viewLifecycleOwner, Observer {
                if(it == GroupViewModel.Authority.MASTER){
                    updateGroup.visibility = View.VISIBLE
                    deleteGroup.visibility = View.VISIBLE
                    membersBtn.visibility = View.VISIBLE
                    outGroup.visibility = View.GONE
                    reportGroup.visibility = View.GONE
                }else if(it == GroupViewModel.Authority.MEMBER){
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
            })

            //버튼 이벤트
            updateGroup.setOnClickListener {
                mActivity.changeFragment(GroupUpdateFragment())
            }

            membersBtn.setOnClickListener {

            }

            deleteGroup.setOnClickListener {
                model.authority.observe(viewLifecycleOwner, Observer {
                    if(it == GroupViewModel.Authority.MASTER){
                        //다이얼로그
                        val bundle = Bundle()
                        bundle.putString("message", "그룹을 삭제하시겠습니까?\n그룹 삭제 시 게시글과 참여 내역은\n자동으로 삭제되지 않습니다.")
                        val modalDialog = ModalDialog()
                        modalDialog.arguments = bundle
                        modalDialog.itemClickListener = object : ModalDialog.OnItemClickListener{
                            override fun OnPositiveClick() {
                                modalDialog.dismiss()
                                //그룹 삭제 완료로 화면 전환
                                val intent = Intent(mActivity, ResultActivity::class.java)
                                intent.putExtra("isType", 2)
                                startActivity(intent)
                            }

                            override fun OnNegativeClick() {
                                modalDialog.dismiss()
                            }
                        }
                        modalDialog.show(mActivity.supportFragmentManager, "ModalDialog")
                    }
                })
            }

            outGroup.setOnClickListener {
                model.authority.observe(viewLifecycleOwner, Observer {
                    if (it == GroupViewModel.Authority.MEMBER) {
                        //다이얼로그
                        val bundle = Bundle()
                        bundle.putString("message", "그룹에서 탈퇴하시겠습니까?\n탈퇴 시 작성된 게시글과 참여내역은\n자동으로 삭제되지 않습니다.")
                        val modalDialog = ModalDialog()
                        modalDialog.arguments = bundle
                        modalDialog.itemClickListener = object : ModalDialog.OnItemClickListener{
                            override fun OnPositiveClick() {
                                modalDialog.dismiss()
                                //그룹 탈퇴 완료로 화면 전환
                                val intent = Intent(mActivity, ResultActivity::class.java)
                                intent.putExtra("isType", 1)
                                startActivity(intent)
                            }

                            override fun OnNegativeClick() {
                                modalDialog.dismiss()
                            }
                        }
                        modalDialog.show(mActivity.supportFragmentManager, "ModalDialog")
                    }
                })
            }

            reportGroup.setOnClickListener {
                val intent = Intent(mActivity, ReportActivity::class.java)
                startActivity(intent)
            }

            backBtn.setOnClickListener {
                mActivity.onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
