package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentCreateVoteFirstBinding
import com.AligatorAPT.DuckBox.view.activity.CreateSurveyActivity
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity
import com.AligatorAPT.DuckBox.view.adapter.createvote.FirstImageRVAdapter
import com.AligatorAPT.DuckBox.viewmodel.CreateVoteViewModel
import java.util.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateVoteFirstFragment: Fragment()  {
    private var _binding : FragmentCreateVoteFirstBinding? = null
    private val binding : FragmentCreateVoteFirstBinding get() = _binding!!
    private var checkValidation = booleanArrayOf(false, false, false, false, false)
    private lateinit var firstImageRVAdapter: FirstImageRVAdapter
    private var list: ArrayList<Bitmap> = arrayListOf()
    var startDate = ""
    var finishDate = ""
    lateinit var start_Datefor: Date
    lateinit var last_Datefor: Date
    private val IMAGE_REQUEST_CODE = 100
    val viewModel : CreateVoteViewModel by activityViewModels()

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
        list.add(createBitmap(1,1))
        firstImageRVAdapter = FirstImageRVAdapter(list, requireContext())

        binding.apply {
            var layoutManager = LinearLayoutManager(requireContext())
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            cvFirstImageRv.layoutManager = layoutManager
            cvFirstImageRv.adapter = firstImageRVAdapter

            firstImageRVAdapter.itemClickListener = object:FirstImageRVAdapter.OnItemClickListener{
                override fun OnRemoveClick(
                    holder: FirstImageRVAdapter.ViewHolder,
                    position: Int
                ) {
                    firstImageRVAdapter.remove(position)
                }

                override fun OnAddClick(
                    holder: FirstImageRVAdapter.ViewHolder,
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

            lateinit var mActivity: Activity
            if(viewModel.isVote.value == true){
                mActivity = activity as CreateVoteActivity
            }else{
                mActivity = activity as CreateSurveyActivity
            }

            list.removeAt(list.size-1)
            //사진 여러개 선택한 경우
            if(data?.clipData != null){
                val count = data.clipData!!.itemCount
                for(i in 0 until count){
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    val source =
                        ImageDecoder.createSource(
                            mActivity.contentResolver,
                            imageUri
                        )
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    list.add(bitmap)
                }
            }else{
                val currentImageUri = data?.data
                currentImageUri?.let {
                    val source =
                        ImageDecoder.createSource(
                            mActivity.contentResolver,
                            currentImageUri
                        )
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    list.add(bitmap)
                }
            }
            list.add(createBitmap(1,1))
            firstImageRVAdapter.notifyDataSetChanged()

            if(viewModel.isVote.value == true){
                setVoteIsActivateBtn()
            }else setSurveyIsActivateBtn()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun initDatePicker() {

        binding.apply {
            cvFirstStartdateCheck.setOnClickListener {
                isFirst = true
                val datePickerDialog = DatePickerFragment.newInstance()
                datePickerDialog.setDatePickerClickListener(object: DatePickerFragment.DatePickerClickListener{
                    override fun onDatePicked(year: Int,month: Int,day: Int,hour: Int,min: Int,cal_ampm: Int,ampm: String) {
                        val date = "$year.${String.format("%02d", month)}.${String.format("%02d", day)} ${String.format("%02d",hour)}:${String.format("%02d",min)} $ampm"
                        startDate = date
                        start_Datefor = Date(year-1900,month-1,day,hour,min,cal_ampm)
                        cvFirstStartdateCheck.setText(startDate)
                    }
                })
                datePickerDialog.setStyle(BottomSheetDialogFragment.STYLE_NORMAL,R.style.CustomBottomSheetDialog)
                datePickerDialog.show(childFragmentManager, datePickerDialog.tag)
            }

            cvFirstLastdateCheck.setOnClickListener {
                isFirst = false
                val datePickerDialog = DatePickerFragment.newInstance()
                datePickerDialog.setDatePickerClickListener(object: DatePickerFragment.DatePickerClickListener{
                    override fun onDatePicked(year: Int,month: Int,day: Int,hour: Int,min: Int,cal_ampm: Int,ampm: String) {
                        val date = "$year.${String.format("%02d", month)}.${String.format("%02d", day)} ${String.format("%02d",hour)}:${String.format("%02d",min)} $ampm"
                        finishDate = date
                        last_Datefor = Date(year-1900,month-1,day,hour,min,cal_ampm)
                        cvFirstLastdateCheck.setText(finishDate)
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
                if(viewModel.isVote.value == true){
                    setVoteIsActivateBtn()
                }else setSurveyIsActivateBtn()
            }
            cvFirstContentEt.doAfterTextChanged {
                checkValidation[1] = cvFirstContentEt.text.toString() != ""
                if(viewModel.isVote.value == true){
                    setVoteIsActivateBtn()
                }else setSurveyIsActivateBtn()
            }
            cvFirstStartdateCheck.doAfterTextChanged {
                checkValidation[2] = cvFirstStartdateCheck.text.toString() != "선택" && checkNow(startDate)
                checkValidation[4] = checkTime()
                if(viewModel.isVote.value == true){
                    setVoteIsActivateBtn()
                }else setSurveyIsActivateBtn()
            }
            cvFirstLastdateCheck.doAfterTextChanged {
                checkValidation[3] = cvFirstLastdateCheck.text.toString() != "선택" && checkNow(finishDate)
                checkValidation[4] = checkTime()
                if(viewModel.isVote.value == true){
                    setVoteIsActivateBtn()
                }else setSurveyIsActivateBtn()
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
        Toast.makeText(context, "현재 시간 1시간 이후로 설정해주세요.", Toast.LENGTH_SHORT).show()
        //TODO 투표 생성 시연할 때 return true
        return true
    }

    private fun checkTime(): Boolean {

        if (startDate != "" && finishDate != "") {

            val startarr = startDate.split(":", ".", " ")
            val finarr = finishDate.split(":", ".", " ")

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
            if(starthour == 12 && finhour < 12) return true
            if(finhour > starthour ) return true
            if(!isNow){
                if(finhour == starthour) {
                    return finmin>startmin
                }
            }
        return false
    }

    fun setVoteIsActivateBtn(){
        binding.apply {
            val mActivity = activity as CreateVoteActivity

            if(checkValidation[0] && checkValidation[1] && checkValidation[2] && checkValidation[3] && checkValidation[4] && list.size>1){
                mActivity.binding.createVoteNextTv.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.main))
                mActivity.binding.createVoteNextTv.isEnabled = true
                mActivity.checkValidation[0] = true

                //bytearray image
                val bytearr : ArrayList<ByteArray> = arrayListOf<ByteArray>()
                for (i in 0 until list.size-1){
                    val imgByteArray: ByteArrayOutputStream? = ByteArrayOutputStream()
                    list[i].compress(Bitmap.CompressFormat.PNG, 2, imgByteArray)
                    if (imgByteArray != null) {
                        bytearr.add(imgByteArray.toByteArray())
                    }
                }
                viewModel.setFirst(cvFirstTitleEt.text.toString(),cvFirstContentEt.text.toString(),start_Datefor,last_Datefor, bytearr)
            }else{
                mActivity.binding.createVoteNextTv.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.darkgray))
                mActivity.binding.createVoteNextTv.isEnabled = false
                mActivity.checkValidation[0] = false
            }
        }
    }

    fun setSurveyIsActivateBtn(){
        binding.apply {
            val mActivity = activity as CreateSurveyActivity

            if(checkValidation[0] && checkValidation[1] && checkValidation[2] && checkValidation[3] && checkValidation[4] && list.size>1){
                mActivity.binding.createSurveyNextTv.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.main))
                mActivity.binding.createSurveyNextTv.isEnabled = true
                mActivity.checkValidation[0] = true

                val bytearr : ArrayList<ByteArray> = arrayListOf<ByteArray>()
                for (i in 0 until list.size-1){
                    val imgByteArray: ByteArrayOutputStream? = ByteArrayOutputStream()
                    list[i].compress(Bitmap.CompressFormat.PNG, 2, imgByteArray)
                    if (imgByteArray != null) {
                        bytearr.add(imgByteArray.toByteArray())
                    }
                }
                viewModel.setFirst(cvFirstTitleEt.text.toString(),cvFirstContentEt.text.toString(),start_Datefor,last_Datefor, bytearr)
            }else{
                mActivity.binding.createSurveyNextTv.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.darkgray))
                mActivity.binding.createSurveyNextTv.isEnabled = false
                mActivity.checkValidation[0] = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}