package com.AligatorAPT.DuckBox.view.fragment.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.AligatorAPT.DuckBox.databinding.FragmentAlarmBinding
import com.AligatorAPT.DuckBox.view.adapter.AlarmListAdapter
import com.AligatorAPT.DuckBox.dto.alarm.AlarmData
import java.util.*

class AlarmFragment : Fragment() {
    private var _binding: FragmentAlarmBinding? = null
    private val binding: FragmentAlarmBinding get() = _binding!!

    private lateinit var alarmAdapter: AlarmListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        binding.apply {
            //alarm list 관리하는 메니저 등록
            alarmAdapter = AlarmListAdapter(setAlarmList())
            alarmAdapter.itemClickListener = object : AlarmListAdapter.OnItemClickListener{
                override fun OnItemClick(
                    holder: AlarmListAdapter.MyViewHolder,
                    view: View,
                    data: AlarmData,
                    position: Int
                ) {
                    //그룹 상세로 화면 전환
                }

                override fun OnDeleteClick(
                    holder: AlarmListAdapter.MyViewHolder,
                    view: View,
                    position: Int
                ) {
                    alarmAdapter.deleteData(position)
                }
            }
            recyclerAlarm.adapter = alarmAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun getDate(year: Int, month: Int, date: Int): Date {
        val cal = Calendar.getInstance()
        cal[year, month - 1] = date
        return Date(cal.timeInMillis)
    }

    private fun setAlarmList(): ArrayList<AlarmData>{
        return arrayListOf<AlarmData>(
            AlarmData("건국대학교", "그룹에 가입했습니다.", Date(), true),
            AlarmData("엘모를 사랑하는 모임", "마감 1일 전 투표가 있습니다.", getDate(2021,1,3), false),
            AlarmData("블록체인 스터디", "설문이 생성되었습니다.", getDate(2022,5,3), false),
            AlarmData("세상에서 제일 이름이 긴 그룹이 되어보려고 이렇게 길게 씁니다", "설문이 생성되었습니다.", getDate(2022,4,1), false),
        )
    }
}
