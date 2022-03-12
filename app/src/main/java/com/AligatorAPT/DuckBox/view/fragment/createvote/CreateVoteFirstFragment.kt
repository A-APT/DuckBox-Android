package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentCreateVoteFirstBinding
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity
import com.AligatorAPT.DuckBox.view.adapter.createvote.FirstImageRVAdapter
import java.util.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CreateVoteFirstFragment: Fragment()  {
    private var _binding : FragmentCreateVoteFirstBinding? = null
    private val binding : FragmentCreateVoteFirstBinding get() = _binding!!
    private var checkValidation = booleanArrayOf(false, false, false, false, false)
    private var isActivateBtn = false
    private lateinit var firstImageRVAdapter: FirstImageRVAdapter
    private var list: ArrayList<Uri> = arrayListOf(Uri.parse("LAST"))
    private var startDate = ""
    private var lastDate = ""

    companion object{
        var isFirst = true
    }


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

        //Time & Date Picker
        initDatePicker()

        //Main Image
        initImage()

        //Check Validation
        check()
    }


    private fun initImage() {
        firstImageRVAdapter = FirstImageRVAdapter(list, requireContext())

        binding.apply {
            var layoutManager = LinearLayoutManager(requireContext())
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            cvFirstImageRv.layoutManager = layoutManager
            cvFirstImageRv.adapter = firstImageRVAdapter

            firstImageRVAdapter.itemClickListener = object:FirstImageRVAdapter.OnItemClickListener{
                override fun OnRemoveClick(
                    holder: FirstImageRVAdapter.ViewHolder,
                    view: View,
                    data: Uri,
                    position: Int
                ) {
                    firstImageRVAdapter.remove(position)
                }

                override fun OnAddClick(
                    holder: FirstImageRVAdapter.ViewHolder,
                    view: View,
                    position: Int
                ) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    intent.action = Intent.ACTION_GET_CONTENT

                    startActivityForResult(intent, 200)

                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 200){
            list.removeAt(list.size-1)

            //사진 여러개 선택한 경우
            if(data?.clipData != null){
                val count = data.clipData!!.itemCount
                for(i in 0 until count){
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    list.add(imageUri)
                }
            }else{
                data?.data?.let{
                    val imageUri : Uri? = data.data
                    if(imageUri != null){
                        list.add(imageUri)
                    }
                }
            }
            list.add(Uri.parse("LAST"))
            firstImageRVAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun initDatePicker() {

        binding.apply {
            cvFirstStartdateCheck.setOnClickListener {
                isFirst = true
                val datePickerDialog = DatePickerFragment.newInstance()
                datePickerDialog.setDatePickerClickListener(object: DatePickerFragment.DatePickerClickListener{
                    override fun onDatePicked(date: String) {
                        startDate = date
                        cvFirstStartdateCheck.setText(date)
                    }
                })
                datePickerDialog.setStyle(BottomSheetDialogFragment.STYLE_NORMAL,R.style.CustomBottomSheetDialog)
                datePickerDialog.show(childFragmentManager, datePickerDialog.tag)
            }

            cvFirstLastdateCheck.setOnClickListener {
                isFirst = false
                val datePickerDialog = DatePickerFragment.newInstance()
                datePickerDialog.setDatePickerClickListener(object: DatePickerFragment.DatePickerClickListener{
                    override fun onDatePicked(date: String) {
                        lastDate = date
                        cvFirstLastdateCheck.setText(date)
                    }
                })
                datePickerDialog.setStyle(BottomSheetDialogFragment.STYLE_NORMAL,R.style.CustomBottomSheetDialog)
                datePickerDialog.show(childFragmentManager, datePickerDialog.tag)
            }
        }
    }

    private fun check() {

        binding.apply {
            cvFirstTitleEt.doAfterTextChanged {
                checkValidation[0] = cvFirstTitleEt.text.toString() != ""
                setIsActivateBtn()
            }
            cvFirstContentEt.doAfterTextChanged {
                checkValidation[1] = cvFirstContentEt.text.toString() != ""
                setIsActivateBtn()
            }
            cvFirstStartdateCheck.doAfterTextChanged {
                Log.e("여기여기","들어왔나ㅏㅏㅏㅏ")
                checkValidation[2] = cvFirstStartdateCheck.text.toString() != "선택"
                checkValidation[4] = checkTime()
                setIsActivateBtn()
            }
            cvFirstLastdateCheck.doAfterTextChanged {
                checkValidation[3] = cvFirstLastdateCheck.text.toString() != "선택"
                checkValidation[4] = checkTime()
                setIsActivateBtn()
            }
        }
    }

    private fun checkTime(): Boolean {

        Log.e("DATE여기여기","시작:"+startDate+"\n"+"끝:"+lastDate)
        if (startDate != "" && lastDate != "") {
            val start_arr = startDate.split(":", ".", " ")
            val fin_arr = lastDate.split(":", ".", " ")

            val start_year = start_arr[0].toInt()
            val start_month = start_arr[1].toInt()
            val start_day = start_arr[2].toInt()
            val start_hour = start_arr[3].toInt()
            val start_min = start_arr[4].toInt()
            val start_ampm = start_arr[5]
            val finish_year = fin_arr[0].toInt()
            val finish_month = fin_arr[1].toInt()
            val finish_day = fin_arr[2].toInt()
            val finish_hour = fin_arr[3].toInt()
            val finish_min = fin_arr[4].toInt()
            val finish_ampm = fin_arr[5]


            if (finish_year >= start_year) {
                if (finish_month >= start_month) {
                    if (finish_day >= start_day) {
                        if (checkAMPM(
                                finish_ampm, start_ampm,
                                finish_hour, start_hour,
                                finish_day, start_day,
                                finish_min, start_min
                            )
                        ) {
                            return true
                        }
                    }
                }
            }
            Toast.makeText(context, "시간 설정을 확인해주세요.", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    private fun checkAMPM(fin : String, start : String,
                      fin_hour : Int, start_hour : Int,
                      fin_day : Int, start_day : Int,
                      fin_min : Int, start_min : Int) : Boolean{
        if(fin=="AM" && start =="PM"){
            return fin_day != start_day
        }else if(fin == "PM" && start == "AM") return true
        else if(fin == start){
            return if(fin_hour>start_hour) true
            else fin_hour==start_hour && fin_min>start_min
        }
        return true
    }

    fun setIsActivateBtn(){
        val mActivity = activity as CreateVoteActivity
        binding.apply {
            if(checkValidation[0] && checkValidation[1] && checkValidation[2] && checkValidation[3] && checkValidation[4]){
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