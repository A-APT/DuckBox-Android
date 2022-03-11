package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentCreateVoteFirstBinding
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity
import com.AligatorAPT.DuckBox.view.adapter.createvote.FirstImageRVAdapter
import com.AligatorAPT.DuckBox.view.fragment.signup.FinishSignUpFragment
import java.util.*

class CreateVoteFirstFragment: Fragment()  {
    private var _binding : FragmentCreateVoteFirstBinding? = null
    private val binding : FragmentCreateVoteFirstBinding get() = _binding!!
    private var checkValidation = booleanArrayOf(false, false, false, false, false, false)
    private var isActivateBtn = false
    private lateinit var firstImageRVAdapter: FirstImageRVAdapter
    private var list: ArrayList<Uri> = arrayListOf(Uri.parse("LAST"))

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
        initImage()
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
                    firstImageRVAdapter.delete(position)
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