package com.AligatorAPT.DuckBox.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {
    lateinit var binding: ActivityReportBinding

    private var isActivateBtn = false
    private var isChecked = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.apply {
            //라디오 버튼
            reportType2.clearCheck()
            reportType3.clearCheck()
            reportType1.setOnCheckedChangeListener(listener1)
            reportType2.setOnCheckedChangeListener(listener2)
            reportType3.setOnCheckedChangeListener(listener3)

            //빈칸 확인
            reportReason.doAfterTextChanged {
                isActivateBtn = reportReason.text.toString() != ""
                setIsActivateBtn()
            }

            //버튼 이벤트
            reportBtn.setOnClickListener {
                if(isActivateBtn){
                    //신고 완료 화면 전환
                    val intent = Intent(this@ReportActivity, ResultActivity::class.java)
                    intent.putExtra("isType", 3)
                    startActivity(intent)
                }
            }

            reportBackBtn.setOnClickListener {
                onBackPressed()
            }
        }

    }

    private fun setCheckImg(){
        binding.apply {
            reportTypeBtn1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
            reportTypeBtn2.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
            reportTypeBtn3.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
            reportTypeBtn4.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
            reportTypeBtn5.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
            reportTypeBtn6.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)

            if(reportTypeBtn1.isChecked){
                reportTypeBtn1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.check_blue,0)
                isChecked = 0
            }else if(reportTypeBtn2.isChecked){
                reportTypeBtn2.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.check_blue,0)
                isChecked = 1
            }else if(reportTypeBtn3.isChecked){
                reportTypeBtn3.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.check_blue,0)
                isChecked = 2
            }else if(reportTypeBtn4.isChecked){
                reportTypeBtn4.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.check_blue,0)
                isChecked = 3
            }else if(reportTypeBtn5.isChecked){
                reportTypeBtn5.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.check_blue,0)
                isChecked = 4
            }else if(reportTypeBtn6.isChecked){
                reportTypeBtn6.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.check_blue,0)
                isChecked = 5
            }
        }
    }

    private val listener1: RadioGroup.OnCheckedChangeListener =
        RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                binding.apply {
                    reportType2.setOnCheckedChangeListener(null)
                    reportType2.clearCheck()
                    reportType3.setOnCheckedChangeListener(null)
                    reportType3.clearCheck()

                    setCheckImg()
                    reportType2.setOnCheckedChangeListener(listener2)
                    reportType3.setOnCheckedChangeListener(listener3)
                }
            }
        }

    private val listener2: RadioGroup.OnCheckedChangeListener =
        RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                binding.apply {
                    reportType1.setOnCheckedChangeListener(null)
                    reportType1.clearCheck()
                    reportType3.setOnCheckedChangeListener(null)
                    reportType3.clearCheck()

                    setCheckImg()
                    reportType1.setOnCheckedChangeListener(listener1)
                    reportType3.setOnCheckedChangeListener(listener3)
                }
            }
        }

    private val listener3: RadioGroup.OnCheckedChangeListener =
        RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                binding.apply {
                    reportType1.setOnCheckedChangeListener(null)
                    reportType1.clearCheck()
                    reportType2.setOnCheckedChangeListener(null)
                    reportType2.clearCheck()

                    setCheckImg()
                    reportType1.setOnCheckedChangeListener(listener1)
                    reportType2.setOnCheckedChangeListener(listener2)
                }
            }
        }

    private fun setIsActivateBtn(){
        binding.apply {
            if(isActivateBtn){
                binding.reportBtn.setBackgroundColor(ContextCompat.getColor(this@ReportActivity, R.color.main))
            }else{
                binding.reportBtn.setBackgroundColor(ContextCompat.getColor(this@ReportActivity, R.color.darkgray))
            }
        }
    }
}
