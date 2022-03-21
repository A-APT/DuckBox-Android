package com.AligatorAPT.DuckBox.view.fragment.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentEmailBinding
import com.AligatorAPT.DuckBox.model.EmailModel
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity

class EmailFragment : Fragment() {
    private var _binding: FragmentEmailBinding? = null
    private val binding: FragmentEmailBinding get() = _binding!!

    private var agree1Flag = false
    private var agree2Flag = false
    private var checkEmail = false
    private var isActivateBtn = false

    private val emailModel: EmailModel = EmailModel()

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
            //버튼 초기화
            agree1.setImageResource(if(agree1Flag) R.drawable.press_check else R.drawable.check)
            agree2.setImageResource(if(agree2Flag) R.drawable.press_check else R.drawable.check)

            //약관동의 클릭 이벤트
            agree1.setOnClickListener {
                agree1.setImageResource(if(agree1Flag) R.drawable.check else R.drawable.press_check)
                agree1Flag = !agree1Flag
                setIsActivateBtn()
            }
            agree2.setOnClickListener {
                agree2.setImageResource(if(agree2Flag) R.drawable.check else R.drawable.press_check)
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

            //이용약관
            termsOfUseBtn.setOnClickListener {
                mActivity.isTermOfUse = true
                mActivity.changeFragment(PolicyFragment(), "이용약관")
            }

            //개인정보처리방침
            privacyPolicyBtn.setOnClickListener {
                mActivity.isTermOfUse = false
                mActivity.changeFragment(PolicyFragment(), "개인정보처리방침")
            }

            //인증하기 버튼 클릭
            emailBtn.setOnClickListener {
                if(isActivateBtn){
                    //이메일 전송
                    emailModel.generateEmailAuth(textEmail.text.toString())

                    //프래그먼트에 이메일 주소 전달
                    setFragmentResult("toEmailCode", bundleOf("email" to textEmail.text.toString()))

                    mActivity.changeFragment(EmailCodeFragment(), "이메일 인증하기")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
