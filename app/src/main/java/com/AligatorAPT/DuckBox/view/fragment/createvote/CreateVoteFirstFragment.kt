package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentCreateVoteFirstBinding
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity
import com.AligatorAPT.DuckBox.view.fragment.signup.FinishSignUpFragment
import java.util.*

class CreateVoteFirstFragment: Fragment()  {
    private var _binding : FragmentCreateVoteFirstBinding? = null
    private val binding : FragmentCreateVoteFirstBinding get() = _binding!!
    private var checkValidation = booleanArrayOf(false, false, false, false, false, false)
    private var isActivateBtn = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateVoteFirstBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDatePicker()
        check()
    }

    private fun initDatePicker() {

        lateinit var mDateSetListener : DatePickerDialog.OnDateSetListener

        binding.cvFirstStartdateCheck.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val datePickerdlg  = DatePickerDialog(
                requireContext(),
                android.R.style.Widget_Holo_DatePicker,
                mDateSetListener,
                year, month, day)
            datePickerdlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerdlg.show()
        }

        mDateSetListener = DatePickerDialog.OnDateSetListener { datePicker: DatePicker?, year: Int, month: Int, day: Int ->

        }
    }

    private fun check() {

        binding.apply {
            //입력값 빈칸 확인d
            cvFirstTitleEt.doAfterTextChanged {
                checkValidation[0] = cvFirstTitleEt.text.toString() != ""
                Log.e("chekcValidation",checkValidation[0].toString())
                setIsActivateBtn()
            }
            cvFirstContentEt.doAfterTextChanged {
                checkValidation[1] = cvFirstContentEt.text.toString() != ""
                setIsActivateBtn()
            }
            cvFirstKeywordTv.doAfterTextChanged {
                checkValidation[1] = cvFirstKeywordTv.text.toString() != "선택"
                setIsActivateBtn()
            }
            cvFirstStartdateCheck.doAfterTextChanged {
                checkValidation[2] = cvFirstKeywordTv.text.toString() != "선택"
                setIsActivateBtn()
            }
            cvFirstLastdateCheck.doAfterTextChanged {
                checkValidation[3] = cvFirstKeywordTv.text.toString() != "선태그"
                setIsActivateBtn()
            }

        }
    }

    private fun setIsActivateBtn(){
        val mActivity = activity as CreateVoteActivity
        binding.apply {
            if(checkValidation[0] && checkValidation[1] ){
                mActivity.binding.createVoteNextTv.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.main))
                mActivity.binding.createVoteNextTv.isEnabled = true
                isActivateBtn = true
            }else{
                mActivity.binding.createVoteNextTv.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.darkgray))
                mActivity.binding.createVoteNextTv.isEnabled = false
                isActivateBtn = false
            }
        }
    }
}