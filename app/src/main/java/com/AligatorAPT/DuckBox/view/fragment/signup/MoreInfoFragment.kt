package com.AligatorAPT.DuckBox.view.fragment.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentMoreInfoBinding
import com.AligatorAPT.DuckBox.dto.user.RegisterDto
import com.AligatorAPT.DuckBox.retrofit.callback.ApiCallback
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity
import com.AligatorAPT.DuckBox.viewmodel.RegisterViewModel
import java.util.regex.Pattern

class MoreInfoFragment : Fragment() {
    private var _binding: FragmentMoreInfoBinding? = null
    private val binding: FragmentMoreInfoBinding get() = _binding!!

    private var checkValidation = booleanArrayOf(false, false, false, false, false)
    private var isActivateBtn = false

    private val model: RegisterViewModel by activityViewModels()

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

    private fun checkPassword(password: String): Boolean {
        //숫자, 특수문자 포함
        val pwdRegex1 = "([0-9].*[!@#^&*()])|([!@#^&*()].*[0-9])"
        //영문자 대소문자가 적어도 하나이상 포함
        val pwdRegex2 = "([a-z].*[A-Z])|([A-Z].*[a-z])"

        val matcher1 = Pattern.compile(pwdRegex1).matcher(password)
        val matcher2 = Pattern.compile(pwdRegex2).matcher(password)

        //유효성 체크
        return if (password.length >= 8 && matcher1.find() && matcher2.find()) {
            binding.errorPassword.visibility = View.INVISIBLE
            true
        } else {
            binding.errorPassword.visibility = View.VISIBLE
            false
        }
    }

    private fun checkRePassword(password: String, rePassword: String): Boolean {
        return if (password == rePassword) {
            binding.errorCheckPassword.visibility = View.INVISIBLE
            true
        } else {
            binding.errorCheckPassword.visibility = View.VISIBLE
            false
        }
    }

    private fun setIsActivateBtn() {
        val mActivity = activity as SignUpActivity
        binding.apply {
            if (checkValidation[0] && checkValidation[1] && checkValidation[2] && checkValidation[3] && checkValidation[4]) {
                binding.finishSignUp.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity,
                        R.color.main
                    )
                )
                isActivateBtn = true
            } else {
                binding.finishSignUp.setBackgroundColor(
                    ContextCompat.getColor(
                        mActivity,
                        R.color.darkgray
                    )
                )
                isActivateBtn = false
            }
        }
    }

    private fun init() {
        val mActivity = activity as SignUpActivity

        binding.apply {
            //스피너 연결
            setDepartment.adapter = ArrayAdapter.createFromResource(
                mActivity, R.array.department, android.R.layout.simple_spinner_item
            )

            setDepartment2.adapter = ArrayAdapter.createFromResource(
                mActivity, R.array.department, android.R.layout.simple_spinner_item
            )

            //입력값 빈칸 확인
            setName.doAfterTextChanged {
                checkValidation[0] = setName.text.toString() != ""
                setIsActivateBtn()
            }
            setStudentId.doAfterTextChanged {
                checkValidation[1] = setStudentId.text.toString() != ""
                setIsActivateBtn()
            }
            setPassword.doAfterTextChanged {
                checkValidation[2] = setPassword.text.toString() != ""
                setIsActivateBtn()
            }
            setRePassword.doAfterTextChanged {
                checkValidation[3] = setRePassword.text.toString() != ""
                setIsActivateBtn()
            }
            setNickname.doAfterTextChanged {
                checkValidation[4] = setNickname.text.toString() != ""
                setIsActivateBtn()
            }

            //버튼 이벤트
            finishSignUp.setOnClickListener {
                if (isActivateBtn) {
                    if (checkPassword(setPassword.text.toString()) && checkRePassword(
                            setPassword.text.toString(),
                            setRePassword.text.toString()
                        )
                    ) {
                        //학과 정보 리스트로 만들기
                        val departmentList = ArrayList<String>()
                        if (setDepartment.selectedItemPosition != 0)
                            departmentList.add(setDepartment.selectedItem.toString())
                        if (setDepartment2.selectedItemPosition != 0)
                            departmentList.add(setDepartment2.selectedItem.toString())

                        model.email.observe(viewLifecycleOwner, Observer {
                            model.registser(
                                RegisterDto(
                                    studentId = setStudentId.text.toString().toInt(),
                                    name = setName.text.toString(),
                                    password = setPassword.text.toString(),
                                    email = it,
                                    phoneNumber = "",
                                    nickname = setNickname.text.toString(),
                                    college = "건국대학교",
                                    department = departmentList
                                ), object : ApiCallback {
                                    override fun apiCallback(flag: Boolean) {
                                        if(flag){
                                            //닉네임 전달
                                            model.setNickname(setNickname.text.toString())
                                            //화면 전환
                                            mActivity.changeFragment(FinishSignUpFragment(), "회원가입 완료")
                                        }
                                    }
                                }
                            )
                        })
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
