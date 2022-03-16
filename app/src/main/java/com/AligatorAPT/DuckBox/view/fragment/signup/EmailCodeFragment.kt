package com.AligatorAPT.DuckBox.view.fragment.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentEmailCodeBinding
import com.AligatorAPT.DuckBox.dto.user.EmailTokenDto
import com.AligatorAPT.DuckBox.model.EmailModel
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity

class EmailCodeFragment : Fragment() {
    private var _binding: FragmentEmailCodeBinding? = null
    private val binding: FragmentEmailCodeBinding get() = _binding!!

    private var isActivateBtn = false
    private var _email = ""

    private val emailModel: EmailModel = EmailModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmailCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //이메일 받기
        setFragmentResultListener("toEmailCode"){key, bundle->
            _email = bundle.getString("email").toString()
            Log.d("RESULT", _email)
        }

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
            userEmail.text = "$_email 으로 코드를 전송했습니다."

            //입력값 확인
            setEmailCode.doAfterTextChanged {
                isActivateBtn = setEmailCode.text.toString() != ""
                setIsActivateBtn()
            }

            //다시보내기 버튼 이벤트
            reSendCodeBtn.setOnClickListener {
                emailModel.generateEmailAuth(_email)
                Toast.makeText(mActivity, "$_email 으로 이메일을 다시 전송했습니다.", Toast.LENGTH_LONG).show()
            }

            //제출 버튼 클릭 이벤트
            emailCodeBtn.setOnClickListener {
                //이메일 코드 확인
                var isVerified = emailModel.verifyEmailToken(EmailTokenDto(email = _email, token = setEmailCode.text.toString()))

                //이메일 코드 임시로 true 설정 (이메일 서버 작동 확인시 지울 예정)
                isVerified = true

                if(isVerified && isActivateBtn){
                    errorEmailCode.visibility = View.INVISIBLE

                    //프래그먼트에 이메일 주소 전달
                    setFragmentResult("toMoreInfo", bundleOf("email" to _email))

                    mActivity.changeFragment(MoreInfoFragment(), "정보 입력하기")
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
