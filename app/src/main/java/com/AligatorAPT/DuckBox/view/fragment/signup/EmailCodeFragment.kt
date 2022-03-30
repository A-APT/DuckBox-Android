package com.AligatorAPT.DuckBox.view.fragment.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentEmailCodeBinding
import com.AligatorAPT.DuckBox.dto.user.EmailTokenDto
import com.AligatorAPT.DuckBox.retrofit.`interface`.ApiCallback
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity
import com.AligatorAPT.DuckBox.viewmodel.RegisterViewModel

class EmailCodeFragment : Fragment() {
    private var _binding: FragmentEmailCodeBinding? = null
    private val binding: FragmentEmailCodeBinding get() = _binding!!

    private var isActivateBtn = false

    private val model: RegisterViewModel by activityViewModels()

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

    private fun setIsActivateBtn() {
        val mActivity = activity as SignUpActivity
        if (isActivateBtn) {
            binding.emailCodeBtn.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.main))
        } else {
            binding.emailCodeBtn.setBackgroundColor(
                ContextCompat.getColor(
                    mActivity,
                    R.color.darkgray
                )
            )
        }
    }

    private fun init() {
        val mActivity = activity as SignUpActivity
        binding.apply {
            //사용자 이메일 설정
            //이메일 받기
            model.email.observe(viewLifecycleOwner, Observer {
                userEmail.text = "$it 으로 코드를 전송했습니다."
            })

            //입력값 확인
            setEmailCode.doAfterTextChanged {
                errorEmailCode.visibility = View.INVISIBLE
                isActivateBtn = setEmailCode.text.toString() != ""
                setIsActivateBtn()
            }

            //다시보내기 버튼 이벤트
            reSendCodeBtn.setOnClickListener {
                model.email.observe(viewLifecycleOwner, Observer {
                    model.generateEmailAuth(it, object: ApiCallback{
                        override fun apiCallback(flag: Boolean) {
                            Toast.makeText(mActivity, "$it 으로 이메일을 다시 전송했습니다.", Toast.LENGTH_LONG).show()
                        }
                    })
                })
            }

            //제출 버튼 클릭 이벤트
            emailCodeBtn.setOnClickListener {
                //이메일 코드 확인
                if (isActivateBtn) {
                    errorEmailCode.visibility = View.INVISIBLE
                    model.email.observe(viewLifecycleOwner, Observer {
                        model.verifyEmailToken(
                            EmailTokenDto(
                                email = it,
                                token = setEmailCode.text.toString()
                            ),
                            object : ApiCallback {
                                override fun apiCallback(flag: Boolean) {
                                    if (flag) {
                                        mActivity.changeFragment(MoreInfoFragment(), "정보 입력하기")
                                    } else {
                                        isActivateBtn = false
                                        errorEmailCode.visibility = View.VISIBLE
                                    }
                                }
                            })
                    })
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
