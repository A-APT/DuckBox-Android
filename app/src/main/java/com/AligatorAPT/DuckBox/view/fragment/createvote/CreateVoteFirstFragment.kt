package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.annotation.SuppressLint
import android.app.Activity
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateVoteFirstFragment: Fragment()  {
    private var _binding : FragmentCreateVoteFirstBinding? = null
    private val binding : FragmentCreateVoteFirstBinding get() = _binding!!
    private var checkValidation = booleanArrayOf(false, false, false, false, false)
    private lateinit var firstImageRVAdapter: FirstImageRVAdapter
    private var list: ArrayList<Uri> = arrayListOf(Uri.parse("LAST"))
    private var startDate = ""
    private var lastDate = ""
    private val IMAGE_REQUEST_CODE = 100

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
                    intent.type = MediaStore.Images.Media.CONTENT_TYPE
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    startActivityForResult(intent, IMAGE_REQUEST_CODE)

                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
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
                checkValidation[2] = cvFirstStartdateCheck.text.toString() != "선택" && checkNow(startDate)
                checkValidation[4] = checkTime()
                setIsActivateBtn()
            }
            cvFirstLastdateCheck.doAfterTextChanged {
                checkValidation[3] = cvFirstLastdateCheck.text.toString() != "선택" && checkNow(lastDate)
                checkValidation[4] = checkTime()
                setIsActivateBtn()
            }
        }
    }

    private fun checkNow(date : String) : Boolean{
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy,MM,dd,hh,mm,a").withLocale(Locale.forLanguageTag("en"))
        val nowDate = formatter.format(now)

        val datearr = date.split(":","."," ")
        val nowarr = nowDate.split(",")

        for(i in 0..2){
            if(datearr[i] < nowarr[i]) break
            else if(datearr[i] > nowarr[i])return true
            else if(i==2) {
                if(checkAMPM(datearr, nowarr,true))return true
            }
        }
        Toast.makeText(context, "현재 시간 이후로 설정해주세요.", Toast.LENGTH_SHORT).show()
        return false
    }

    private fun checkTime(): Boolean {

        if (startDate != "" && lastDate != "") {

            val startarr = startDate.split(":", ".", " ")
            val finarr = lastDate.split(":", ".", " ")

            for(i in 0..2){
                if(finarr[i] < startarr[i])break
                else if(finarr[i] > startarr[i])return true
                else if(i==2){
                     if(checkAMPM(finarr, startarr,false))return true
                }
            }
            Toast.makeText(context, "시간 설정을 확인해주세요.", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    private fun checkAMPM(datearr : List<String>, nowarr : List<String>, isNow : Boolean) : Boolean{
        val finampm = datearr[5]
        val startampm = nowarr[5]
        val finday = datearr[2].toInt()
        val startday = nowarr[2].toInt()
        val finhour = datearr[3].toInt()
        val starthour = nowarr[3].toInt()
        val finmin = datearr[4].toInt()
        val startmin = nowarr[4].toInt()

        if(finampm == "AM" && startampm =="PM") return finday != startday
        else if(finampm == "PM" && startampm == "AM") return true
        else if(finampm == startampm)
            if(finhour > starthour) return true
            else if(finhour==starthour) {
                return if(isNow) finmin>=startmin
                else finmin>startmin
            }
        return false
    }

    fun setIsActivateBtn(){
        val mActivity = activity as CreateVoteActivity
        mActivity.
        binding.apply {
            if(checkValidation[0] && checkValidation[1] && checkValidation[2] && checkValidation[3] && checkValidation[4]){
                mActivity.binding.createVoteNextTv.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.main))
                mActivity.binding.createVoteNextTv.isEnabled = true
                mActivity.checkValidation[0] = true
            }else{
                mActivity.binding.createVoteNextTv.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.darkgray))
                mActivity.binding.createVoteNextTv.isEnabled = false
                mActivity.checkValidation[0] = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}