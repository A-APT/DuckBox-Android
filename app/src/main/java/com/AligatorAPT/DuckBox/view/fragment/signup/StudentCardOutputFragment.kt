package com.AligatorAPT.DuckBox.view.fragment.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.AligatorAPT.DuckBox.databinding.FragmentStudentCardOutputBinding
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity

class StudentCardOutputFragment : Fragment() {
    private var _binding: FragmentStudentCardOutputBinding? = null
    private val binding: FragmentStudentCardOutputBinding get() = _binding!!

    private var userName = "홍길동"
    private var studentId = "201911111"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentCardOutputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        val mActivity = activity as SignUpActivity
        binding.apply {
            //이름, 학번 설정
            setUserName.text = userName
            setStudentId.text = studentId

            studentCardOutputBtn.setOnClickListener {
                mActivity.changeFragment(MoreInfoFragment(), "추가정보 입력하기")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
