package com.AligatorAPT.DuckBox.view.fragment.navigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentHomeBinding
import com.AligatorAPT.DuckBox.view.activity.*
import com.AligatorAPT.DuckBox.view.adapter.MyGroupAdapter
import com.AligatorAPT.DuckBox.view.adapter.PaperListAdapter
import com.AligatorAPT.DuckBox.view.data.MyGroupData
import com.AligatorAPT.DuckBox.view.data.PaperListData
import com.AligatorAPT.DuckBox.view.dialog.ModalDialog

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private lateinit var myGroupAdapter: MyGroupAdapter
    private lateinit var paperListAdapter: PaperListAdapter

    private var isParticipation = true
    private var isVerification = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        val mActivity = activity as NavigationActivity

        binding.apply {
            createGroupBtn.setOnClickListener {
                if(isVerification){
                    //그룹 만들기 액티비티 이동
                    val intent = Intent(activity, CreateGroupActivity::class.java)
                    startActivity(intent)
                }else{
                    //다이얼로그
                    val bundle = Bundle()
                    bundle.putString("message", "모든 인증을 완료하고\n" +
                            "그룹을 만들어주세요.")
                    val modalDialog = ModalDialog()
                    modalDialog.arguments = bundle
                    modalDialog.itemClickListener = object : ModalDialog.OnItemClickListener{
                        override fun OnPositiveClick() {
                            modalDialog.dismiss()
                            //내정보로 이동
                            mActivity.selectedBottomNavigationItem(R.id.tab_my)
                        }

                        override fun OnNegativeClick() {
                            modalDialog.dismiss()
                        }
                    }
                    modalDialog.show(mActivity.supportFragmentManager, "ModalDialog")
                }
            }

            //MyGroup list 관리하는 메니저 등록
            myGroupAdapter = MyGroupAdapter(setGroupList())
            myGroupAdapter.itemClickListener = object :MyGroupAdapter.OnItemClickListener{
                override fun OnItemClick(
                    holder: MyGroupAdapter.MyViewHolder,
                    view: View,
                    data: MyGroupData,
                    position: Int
                ) {
                    //그룹 상세로 화면 전환
                    val intent = Intent(activity, GroupDetailActivity::class.java)
                    intent.putExtra("groupData", data)
                    startActivity(intent)
                }
            }
            recyclerMyGroup.adapter = myGroupAdapter

            //paper list 관리하는 메니저 등록
            paperListAdapter = PaperListAdapter(setPaperList())
            paperListAdapter.itemClickListener = object :PaperListAdapter.OnItemClickListener{
                override fun OnItemClick(
                    holder: PaperListAdapter.MyViewHolder,
                    view: View,
                    data: PaperListData,
                    position: Int
                ) {
                    // 투표 및 설문 상세로 화면 전환
                    if(data.isVote){
                        val intent = Intent(activity, VoteDetailActivity::class.java)
                        startActivity(intent)
                    }else{
                        val intent = Intent(activity, PollDetailActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            recyclerPaperList.adapter = paperListAdapter

            binding.apply {
                //참여 가능 버튼 누를 경우
                toggleParticipationPossible.setOnClickListener {
                    if(!isParticipation){
                        toggleParticipationCompleted.setTextColor(ContextCompat.getColor(mActivity, R.color.darkgray))
                        toggleParticipationCompleted.setBackgroundResource(0)
                        toggleParticipationPossible.setTextColor(ContextCompat.getColor(mActivity, R.color.black))
                        toggleParticipationPossible.setBackgroundResource(R.drawable.white_color_box_50dp)
                        isParticipation = !isParticipation
                        paperListAdapter.setData(setPaperList())
                    }
                }
                //참여 완료 버튼 누를 경우
                toggleParticipationCompleted.setOnClickListener {
                    if(isParticipation){
                        toggleParticipationPossible.setTextColor(ContextCompat.getColor(mActivity, R.color.darkgray))
                        toggleParticipationPossible.setBackgroundResource(0)
                        toggleParticipationCompleted.setTextColor(ContextCompat.getColor(mActivity, R.color.black))
                        toggleParticipationCompleted.setBackgroundResource(R.drawable.white_color_box_50dp)
                        isParticipation = !isParticipation
                        paperListAdapter.setData(setParticipationPaperList())
                    }
                }
            }
        }
    }

    private fun setGroupList(): ArrayList<MyGroupData>{
        return arrayListOf<MyGroupData>(
            MyGroupData(R.drawable.community, "KU 총학생회"),
            MyGroupData(R. drawable.community, "악어아파트"),
            MyGroupData(R.drawable.community, "SecurityFact"),
            MyGroupData(R.drawable.community, "연합봉사동아리"),
            MyGroupData(R.drawable.community, "오리박스"),
        )
    }

    private fun setPaperList(): ArrayList<PaperListData>{
        return arrayListOf<PaperListData>(
            PaperListData(R.drawable.sub4_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", 100, 50),
            PaperListData(R.drawable.sub1_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", 100, 50),
            PaperListData(R.drawable.sub2_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", 100, 50),
            PaperListData(R.drawable.sub5_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", 100, 50),
        )
    }
    private fun setParticipationPaperList(): ArrayList<PaperListData>{
        return arrayListOf<PaperListData>(
            PaperListData(R.drawable.sub5_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, false, "3일 06:05:03 남음", 100, 50),
            PaperListData(R.drawable.sub1_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, false, "3일 06:05:03 남음", 100, 50),
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
