package com.AligatorAPT.DuckBox.view.fragment.signup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.AligatorAPT.DuckBox.MainActivity
import com.AligatorAPT.DuckBox.databinding.FragmentFinishSignUpBinding
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity

class FinishSignUpFragment : Fragment() {
    private var _binding: FragmentFinishSignUpBinding? = null
    private val binding: FragmentFinishSignUpBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinishSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        val mActivity = activity as SignUpActivity
        binding.apply {
            //유저 닉네임 설정
            finishUserName.text = "노랑오리"

            //시작하기 버튼 이벤트
            startHome.setOnClickListener {
                val intent = Intent(mActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
