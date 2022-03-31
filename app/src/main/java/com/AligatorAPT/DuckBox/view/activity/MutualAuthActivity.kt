package com.AligatorAPT.DuckBox.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.AligatorAPT.DuckBox.databinding.ActivityMutualAuthBinding
import com.AligatorAPT.DuckBox.view.adapter.MutualAuthAdapter
import com.AligatorAPT.DuckBox.view.data.MutualAuthData

class MutualAuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityMutualAuthBinding

    private lateinit var mutualAuthAdapter : MutualAuthAdapter
    private var _groupName = ""
    private var _groupDescription = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMutualAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        //intent
        _groupName = intent.getStringExtra("groupName")!!
        _groupDescription = intent.getStringExtra("groupDescription")!!

        //list 어뎁터 등록
        mutualAuthAdapter = MutualAuthAdapter(setMutualAuthList())
        mutualAuthAdapter.itemClickListener = object: MutualAuthAdapter.OnItemClickListener{
            override fun OnItemClick(
                holder: MutualAuthAdapter.MyViewHolder,
                view: View,
                data: MutualAuthData,
                position: Int
            ) {
                //승인하기 버튼 이벤트
                mutualAuthAdapter.deleteData(position)
            }
        }

        binding.apply {
            recyclerView.adapter = mutualAuthAdapter

            //그룹 정보
            groupTitle.text = _groupName
            groupDescription.text = _groupDescription

            //버튼 이벤트
            backBtn.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun setMutualAuthList(): ArrayList<MutualAuthData>{
        return arrayListOf(
            MutualAuthData(name="홍길동", email="abc@konkuk.ac.kr", studentId = 201911111),
            MutualAuthData(name="김길동", email="abc@konkuk.ac.kr", studentId = 201911111),
            MutualAuthData(name="박길동", email="abc@konkuk.ac.kr", studentId = 201911111),
            MutualAuthData(name="이길동", email="abc@konkuk.ac.kr", studentId = 201911111),
            MutualAuthData(name="최길동", email="abc@konkuk.ac.kr", studentId = 201911111),
            MutualAuthData(name="백길동", email="abc@konkuk.ac.kr", studentId = 201911111),
        )
    }
}
