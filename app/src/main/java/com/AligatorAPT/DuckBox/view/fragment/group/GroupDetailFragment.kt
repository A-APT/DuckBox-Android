package com.AligatorAPT.DuckBox.view.fragment.group

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentGroupDetailBinding
import com.AligatorAPT.DuckBox.view.activity.*
import com.AligatorAPT.DuckBox.view.adapter.PaperListAdapter
import com.AligatorAPT.DuckBox.view.data.PaperListData
import com.AligatorAPT.DuckBox.view.dialog.ModalDialog
import com.AligatorAPT.DuckBox.viewmodel.GroupDetailViewModel
import com.google.android.material.tabs.TabLayout

class GroupDetailFragment : Fragment() {
    private var _binding: FragmentGroupDetailBinding? = null
    private val binding: FragmentGroupDetailBinding get() = _binding!!

    private val model: GroupDetailViewModel by activityViewModels()

    private lateinit var paperListAdapter: PaperListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init(){
        val mActivity = activity as GroupActivity

        //그룹 정보 초기화
        model.setGroupInfo(
            _name = "KU총학생회",
            _description = "2022 건국대학교 총학생회입니다.",
            _header = "",
            _id = "1",
            _leader = "jiwoo",
            _profile = "",
            _status = "PENDING"
        )
        //그룹 권한 초기화
        model.setAuthority(GroupDetailViewModel.Authority.OTHER)

        binding.apply {
            //그룹 정보 추가
            model.name.observe(viewLifecycleOwner, Observer {
                groupTitle.text = it
            })
            model.description.observe(viewLifecycleOwner, Observer {
                groupDescription.text = it
            })

            //이미지

            //그룹 가입 여부
            model.authority.observe(viewLifecycleOwner, Observer {
                if (it == GroupDetailViewModel.Authority.MEMBER || it == GroupDetailViewModel.Authority.MASTER) {
                    joinGroup.visibility = View.GONE
                    mutualAuthentication.visibility = View.VISIBLE
                }else{
                    joinGroup.visibility = View.VISIBLE
                    mutualAuthentication.visibility = View.GONE
                }
            })

            //버튼 이벤트
            mutualAuthentication.setOnClickListener {
                model.authority.observe(viewLifecycleOwner, Observer {
                    if (it == GroupDetailViewModel.Authority.MEMBER || it == GroupDetailViewModel.Authority.MASTER) {
                        mActivity.changeFragment(MutualAuthFragment())
                    }
                })
            }

            joinGroup.setOnClickListener {
                model.authority.observe(viewLifecycleOwner, Observer {
                    if (it == GroupDetailViewModel.Authority.OTHER) {
                        //다이얼로그
                        val bundle = Bundle()
                        bundle.putString("message", "그룹에 가입하시겠습니까?")
                        val modalDialog = ModalDialog()
                        modalDialog.arguments = bundle
                        modalDialog.itemClickListener = object : ModalDialog.OnItemClickListener{
                            override fun OnPositiveClick() {
                                modalDialog.dismiss()
                                //그룹 가입 요청 완료로 화면 전환
                                val intent = Intent(mActivity, ResultActivity::class.java)
                                intent.putExtra("isType", 0)
                                model.name.observe(viewLifecycleOwner, Observer {
                                    intent.putExtra("groupName", it)
                                })
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

            backBtn.setOnClickListener {
                mActivity.onBackPressed()
            }

            groupSetting.setOnClickListener {
                //그룹 설정로 화면 전환
                mActivity.changeFragment(GroupSettingFragment())
            }

            //pager list 매니저 등록
            paperListAdapter = PaperListAdapter(setPaperList(0))
            paperListAdapter.itemClickListener = object : PaperListAdapter.OnItemClickListener{
                override fun OnItemClick(
                    holder: PaperListAdapter.MyViewHolder,
                    view: View,
                    data: PaperListData,
                    position: Int
                ) {
                    model.authority.observe(viewLifecycleOwner, Observer {
                        if (it == GroupDetailViewModel.Authority.OTHER) {
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
                            modalDialog.show(mActivity.supportFragmentManager, "ModalDialog")
                        }else{
                            // 투표 및 설문 상세로 화면 전환
                            if(data.isVote){
                                val intent = Intent(mActivity, VoteDetailActivity::class.java)
                                startActivity(intent)
                            }else{
                                val intent = Intent(mActivity, PollDetailActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    })
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
