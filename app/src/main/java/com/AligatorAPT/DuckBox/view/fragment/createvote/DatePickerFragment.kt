package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.DialogTimedatepickerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment : BottomSheetDialogFragment() {
    lateinit var binding: DialogTimedatepickerBinding
    var arr : List<String> = listOf()

    companion object {
        fun newInstance() : DatePickerFragment{
            return DatePickerFragment()
        }
    }

    interface DatePickerClickListener{
        fun onDatePicked(date : String)
    }

    private lateinit var mDatePickerClickListener : DatePickerClickListener

    fun setDatePickerClickListener(datePickerclickListener: DatePickerClickListener){
        mDatePickerClickListener = datePickerclickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogTimedatepickerBinding.inflate(inflater, container, false)

        if(!CreateVoteFirstFragment.isFirst)binding.dialogTitleTv.text = "마감 시간"

        initDatePickerSetting()
        initBtns()

        return binding.root
    }

    fun initDatePickerYear(){
        val calendar = GregorianCalendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.YEAR,0)
        binding.dialogYearNp.minValue = calendar.get(Calendar.YEAR)
        calendar.add(Calendar.YEAR, 10)
        binding.dialogYearNp.maxValue = calendar.get(Calendar.YEAR)
    }

    fun initDatePickerMonth(){
        binding.dialogMonthNp.minValue = 1
        binding.dialogMonthNp.maxValue = 12
    }

    fun initDatePickerDay(calendar: Calendar){
        binding.dialogDayNp.minValue = 1
        binding.dialogDayNp.maxValue = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    fun initDatePickerHour(){
        binding.dialogHourNp.minValue = 1
        binding.dialogHourNp.maxValue = 12
    }

    fun initDatePickerMin(){
        binding.dialogMinNp.minValue = 0
        binding.dialogMinNp.maxValue = 59
        val min_list = resources.getStringArray(R.array.minute)
        binding.dialogMinNp.displayedValues = min_list
    }

    fun initDatePickerAMPM(){
        binding.dialogAmpmNp.minValue = 0
        binding.dialogAmpmNp.maxValue = 1
        binding.dialogAmpmNp.displayedValues = arrayOf("AM", "PM")
    }

    fun initDatePickerSetting(){
        val calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"))
        val year = binding.dialogYearNp
        val month = binding.dialogMonthNp
        val day = binding.dialogDayNp
        val hour = binding.dialogHourNp
        val min = binding.dialogMinNp
        val ampm = binding.dialogAmpmNp

        // 순환 안되게 막기
        year.wrapSelectorWheel = false
        month.wrapSelectorWheel = false
        day.wrapSelectorWheel = false
        hour.wrapSelectorWheel = false
        min.wrapSelectorWheel = false
        ampm.wrapSelectorWheel = false

        // 꾹 눌러서 editText 전환되는거 막기
        year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        month.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        day.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        hour.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        min.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        ampm.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        initDatePickerYear()
        initDatePickerMonth()
        initDatePickerDay(calendar)
        initDatePickerHour()
        initDatePickerMin()
        initDatePickerAMPM()

        // 오늘 날짜로 표시
        year.value = calendar.get(Calendar.YEAR)
        month.value = calendar.get(Calendar.MONTH) + 1
        day.value = calendar.get(Calendar.DAY_OF_MONTH)
        val cal_hour= calendar.get(Calendar.HOUR)
        if(cal_hour == 0){
            hour.value = 12
        }
        min.value = calendar.get(Calendar.MINUTE)
        ampm.value = calendar.get(Calendar.AM_PM)

        binding.dialogYearNp.setOnValueChangedListener{picker, oldVal, newVal ->
            calendar.set(Calendar.YEAR, newVal)
            initDatePickerDay(calendar)
        }

        binding.dialogMonthNp.setOnValueChangedListener { picker, oldVal, newVal ->
            calendar.set(Calendar.MONTH, newVal - 1)
            initDatePickerDay(calendar)
        }
    }

    fun initBtns() {
        binding.dialogOkTv.setOnClickListener {

            val year = binding.dialogYearNp.value
            val month = binding.dialogMonthNp.value
            val day = binding.dialogDayNp.value
            val hour = binding.dialogHourNp.value
            val min = binding.dialogMinNp.value
            val cal_ampm = binding.dialogAmpmNp.value
            val ampm = if(cal_ampm==0) "AM" else "PM"

            val date = "$year.${String.format("%02d", month)}.${String.format("%02d", day)} ${String.format("%02d",hour)}:${String.format("%02d",min)} $ampm"
            mDatePickerClickListener.onDatePicked(date)
            dismiss()
        }
    }
}