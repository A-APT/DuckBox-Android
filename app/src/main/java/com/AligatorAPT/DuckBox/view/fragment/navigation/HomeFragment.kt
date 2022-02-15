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
import com.AligatorAPT.DuckBox.view.adapter.VoteAndPollListAdapter
import com.AligatorAPT.DuckBox.view.data.MyGroupData
import com.AligatorAPT.DuckBox.view.data.VoteAndPollListData

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private lateinit var myGroupAdapter: MyGroupAdapter
    private var myGroupArray = ArrayList<MyGroupData>()

    private lateinit var voteListAdapter: VoteAndPollListAdapter
    private var voteListArray = ArrayList<VoteAndPollListData>()

    private var isParticipation = true

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
        binding.apply {
            //myGroup&voteList array data 초기화
            arrayInitialization()

            //MyGroup list 관리하는 메니저 등록
            myGroupAdapter = MyGroupAdapter(myGroupArray)
            myGroupAdapter.itemClickListener = object :MyGroupAdapter.OnItemClickListener{
                override fun OnItemClick(
                    holder: MyGroupAdapter.MyViewHolder,
                    view: View,
                    data: MyGroupData,
                    position: Int
                ) {
                    //그룹 상세로 화면 전환
                    val intent = Intent(activity, GroupDetailActivity::class.java)
                    startActivity(intent)
                }
            }
            recyclerMyGroup.adapter = myGroupAdapter

            //voteAndPoll list 관리하는 메니저 등록
            voteListAdapter = VoteAndPollListAdapter(voteListArray)
            voteListAdapter.itemClickListener = object :VoteAndPollListAdapter.OnItemClickListener{
                override fun OnItemClick(
                    holder: VoteAndPollListAdapter.MyViewHolder,
                    view: View,
                    data: VoteAndPollListData,
                    position: Int
                ) {
                    // 투표 및 설문 상세로 화면 전환
                    if(voteListArray[position].isVote){
                        val intent = Intent(activity, VoteDetailActivity::class.java)
                        startActivity(intent)
                    }else{
                        val intent = Intent(activity, PollDetailActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            recyclerVoteAndPollList.adapter = voteListAdapter

            val mActivity = activity as NavigationActivity
            binding.apply {
                //참여 가능 버튼 누를 경우
                toggleParticipationPossible.setOnClickListener {
                    if(!isParticipation){
                        toggleParticipationCompleted.setTextColor(ContextCompat.getColor(mActivity, R.color.darkgray))
                        toggleParticipationCompleted.setBackgroundResource(0)
                        toggleParticipationPossible.setTextColor(ContextCompat.getColor(mActivity, R.color.black))
                        toggleParticipationPossible.setBackgroundResource(R.drawable.white_color_box_50dp)
                        isParticipation = !isParticipation
                        voteListArrayInitialization()
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
                        voteListArrayInitialization()
                    }
                }
            }
        }
    }

    private fun arrayInitialization(){
        myGroupArray.add(MyGroupData(R.drawable.community, "KU 총학생회"))
        myGroupArray.add(MyGroupData(R. drawable.community, "악어아파트"))
        myGroupArray.add(MyGroupData(R.drawable.community, "SecurityFact"))
        myGroupArray.add(MyGroupData(R.drawable.community, "연합봉사동아리"))
        myGroupArray.add(MyGroupData(R.drawable.community, "오리박스"))

        voteListArray.add(VoteAndPollListData(R.drawable.press_check, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", "20.36% ", "(851명)"))
        voteListArray.add(VoteAndPollListData(R.drawable.press_check, "유기견 보호소 정기 봉사 날짜 선정 설문조사", "연합봉사동아리", false, true, "3일 06:05:03 남음", "20.36% ", "(851명)"))
        voteListArray.add(VoteAndPollListData(R.drawable.press_check, "건국대학교 제47회 인문대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", "20.36% ", "(851명)"))
        voteListArray.add(VoteAndPollListData(R.drawable.press_check, "건국대학교 제47회 예술대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", "20.36% ", "(851명)"))
    }

    private fun voteListArrayInitialization(){
        voteListAdapter.clearData()
        if(isParticipation){
            voteListAdapter.getData().add(VoteAndPollListData(R.drawable.press_check, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", "20.36% ", "(851명)"))
            voteListAdapter.getData().add(VoteAndPollListData(R.drawable.press_check, "유기견 보호소 정기 봉사 날짜 선정 설문조사", "연합봉사동아리", false, true, "3일 06:05:03 남음", "20.36% ", "(851명)"))
            voteListAdapter.getData().add(VoteAndPollListData(R.drawable.press_check, "건국대학교 제47회 인문대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", "20.36% ", "(851명)"))
            voteListAdapter.getData().add(VoteAndPollListData(R.drawable.press_check, "건국대학교 제47회 예술대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", "20.36% ", "(851명)"))
            voteListAdapter.notifyChanged()
        }else{
            voteListAdapter.getData().add(VoteAndPollListData(R.drawable.press_check, "건국대학교 제46회 공과대학 학생회 투표", "KU총학생회", true, false, "3일 06:05:03 남음", "20.36% ", "(851명)"))
            voteListAdapter.getData().add(VoteAndPollListData(R.drawable.press_check, "건국대학교 제45회 공과대학 학생회 투표", "KU총학생회", true, false, "3일 06:05:03 남음", "20.36% ", "(851명)"))
            voteListAdapter.notifyChanged()
        }
    }
}
