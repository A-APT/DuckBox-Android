package com.AligatorAPT.DuckBox.view.fragment.SignUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentEmailCodeBinding
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity

class EmailCodeFragment : Fragment() {
    private var _binding: FragmentEmailCodeBinding? = null
    private val binding: FragmentEmailCodeBinding get() = _binding!!

    private var isActivateBtn = false
    private var correctCode = "123456"
    private var userEmail = "duckBox@konkuk.ac.kr"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmailCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun setIsActivateBtn(){
        val mActivity = activity as SignUpActivity
        if(isActivateBtn){
            binding.emailCodeBtn.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.main))
        }else{
            binding.emailCodeBtn.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.darkgray))
        }
    }

    private fun init(){
        val mActivity = activity as SignUpActivity
        binding.apply {
            //사용자 이메일 설정
            userEmail.text = "$userEmail 으로 코드를 전송했습니다."

            //입력값 확인
            setEmailCode.doAfterTextChanged {
                isActivateBtn = setEmailCode.text.toString() != ""
                setIsActivateBtn()
            }

            //제출 버튼 클릭 이벤트
            emailCodeBtn.setOnClickListener {
                if(setEmailCode.text.toString() == correctCode && isActivateBtn){
                    errorEmailCode.visibility = View.INVISIBLE
                    mActivity.changeFragment(ExplainStudentCardFragment(), "학생증 인증하기")
                }else{
                    isActivateBtn = false
                    errorEmailCode.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
