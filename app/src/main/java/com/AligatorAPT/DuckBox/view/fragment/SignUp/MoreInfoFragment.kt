package com.AligatorAPT.DuckBox.view.fragment.SignUp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentMoreInfoBinding
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity
import java.util.regex.Pattern

class MoreInfoFragment : Fragment() {
    private var _binding: FragmentMoreInfoBinding? = null
    private val binding: FragmentMoreInfoBinding get() = _binding!!

    private var checkValidation = booleanArrayOf(false, false, false, false)
    private var isActivateBtn = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun checkPassword(password:String):Boolean{
        //숫자, 특수문자 포함
        val pwdRegex1 = "([0-9].*[!@#^&*()])|([!@#^&*()].*[0-9])"
        //영문자 대소문자가 적어도 하나이상 포함
        val pwdRegex2 = "([a-z].*[A-Z])|([A-Z].*[a-z])"

        val matcher1 = Pattern.compile(pwdRegex1).matcher(password)
        val matcher2 = Pattern.compile(pwdRegex2).matcher(password)

        //유효성 체크
        return if(password.length>=8 && matcher1.find() && matcher2.find()){
            binding.errorPassword.visibility = View.INVISIBLE
            true
        }else{
            binding.errorPassword.visibility = View.VISIBLE
            false
        }
    }

    private fun checkRePassword(password: String, rePassword:String):Boolean{
        return if(password == rePassword){
            binding.errorCheckPassword.visibility = View.INVISIBLE
            true
        }else{
            binding.errorCheckPassword.visibility = View.VISIBLE
            false
        }
    }

    private fun setIsActivateBtn(){
        val mActivity = activity as SignUpActivity
        binding.apply {
            if(checkValidation[0] && checkValidation[1] && checkValidation[2] && checkValidation[3]){
                binding.finishSignUp.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.main))
                isActivateBtn = true
            }else{
                binding.finishSignUp.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.darkgray))
                isActivateBtn = false
            }
        }
    }

    private fun init(){
        val mActivity = activity as SignUpActivity

        binding.apply {
            //입력값 빈칸 확인d
            setPassword.doAfterTextChanged {
                checkValidation[0] = setPassword.text.toString() != ""
                setIsActivateBtn()
            }
            setRePassword.doAfterTextChanged {
                checkValidation[1] = setRePassword.text.toString() != ""
                setIsActivateBtn()
            }
            setNickname.doAfterTextChanged {
                checkValidation[2] = setNickname.text.toString() != ""
                setIsActivateBtn()
            }
            setDepartment.doAfterTextChanged {
                checkValidation[3] = setDepartment.text.toString() != ""
                setIsActivateBtn()
            }

            //버튼 이벤트
            finishSignUp.setOnClickListener {
                if(isActivateBtn){
                    if (checkPassword(setPassword.text.toString()) && checkRePassword(setPassword.text.toString(), setRePassword.text.toString())){
                        mActivity.changeFragment(FinishSignUpFragment())
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}