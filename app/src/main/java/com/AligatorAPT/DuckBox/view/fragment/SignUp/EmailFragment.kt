package com.AligatorAPT.DuckBox.view.fragment.SignUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentEmailBinding
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity

class EmailFragment : Fragment() {
    private var _binding: FragmentEmailBinding? = null
    private val binding: FragmentEmailBinding get() = _binding!!

    private var agree1Flag = false
    private var agree2Flag = false
    private var checkEmail = false
    private var isActivateBtn = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun setIsActivateBtn(){
        val mActivity = activity as SignUpActivity
        if(agree1Flag&& agree2Flag && checkEmail){
            binding.emailBtn.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.main))
            isActivateBtn = true
        }else{
            binding.emailBtn.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.darkgray))
            isActivateBtn = false
        }
    }

    private fun init(){
        val mActivity = activity as SignUpActivity
        binding.apply {
            //약관동의 클릭 이벤트
            agree1.setOnClickListener {
                if(agree1Flag){
                    agree1.setImageResource(R.drawable.check)
                }else{
                    agree1.setImageResource(R.drawable.press_check)
                }
                agree1Flag = !agree1Flag
                setIsActivateBtn()
            }
            agree2.setOnClickListener {
                if(agree2Flag){
                    agree2.setImageResource(R.drawable.check)
                }else{
                    agree2.setImageResource(R.drawable.press_check)
                }
                agree2Flag = !agree2Flag
                setIsActivateBtn()
            }

            //이메일 형식 확인
            textEmail.doAfterTextChanged {
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(textEmail.text.toString()).matches()){
                    errorEmail.visibility = View.VISIBLE
                    checkEmail = false
                }else{
                    errorEmail.visibility = View.INVISIBLE
                    checkEmail = true
                }
                setIsActivateBtn()
            }

            //인증하기 버튼 클릭
            emailBtn.setOnClickListener {
                if(isActivateBtn){
                    mActivity.changeFragment(EmailCodeFragment())
                }
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}