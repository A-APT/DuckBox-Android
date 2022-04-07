package com.AligatorAPT.DuckBox.view.fragment.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.AligatorAPT.DuckBox.databinding.FragmentMutualAuthBinding
import com.AligatorAPT.DuckBox.view.activity.GroupActivity
import com.AligatorAPT.DuckBox.view.adapter.MutualAuthAdapter
import com.AligatorAPT.DuckBox.view.data.MutualAuthData
import com.AligatorAPT.DuckBox.viewmodel.GroupViewModel

class MutualAuthFragment : Fragment() {
    private var _binding: FragmentMutualAuthBinding? = null
    private val binding: FragmentMutualAuthBinding get() = _binding!!

    private val model: GroupViewModel by activityViewModels()

    private lateinit var mutualAuthAdapter : MutualAuthAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMutualAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init(){
        val mActivity = activity as GroupActivity

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
            //그룹 정보 추가
            model.name.observe(viewLifecycleOwner, Observer {
                groupTitle.text = it
            })
            model.description.observe(viewLifecycleOwner, Observer {
                groupDescription.text = it
            })


            //버튼 이벤트
            backBtn.setOnClickListener {
                mActivity.onBackPressed()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
