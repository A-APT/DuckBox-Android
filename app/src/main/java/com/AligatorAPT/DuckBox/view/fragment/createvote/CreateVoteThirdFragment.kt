package com.AligatorAPT.DuckBox.view.fragment.createvote

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import com.AligatorAPT.DuckBox.databinding.FragmentCreateVoteThirdBinding
import com.AligatorAPT.DuckBox.R
import android.content.Intent
import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import androidx.fragment.app.viewModels
import com.AligatorAPT.DuckBox.view.data.ExcelData
import com.AligatorAPT.DuckBox.viewmodel.createvote.CVSecondListViewModel
import java.io.FileNotFoundException
import java.io.InputStream
import jxl.Sheet
import jxl.Workbook
import jxl.read.biff.BiffException
import java.io.IOException


class CreateVoteThirdFragment: Fragment()  {
    private var _binding : FragmentCreateVoteThirdBinding? = null
    private val binding : FragmentCreateVoteThirdBinding get() = _binding!!
    private val FILE_REQUEST_CODE = 100
    private var filename : String? = null
    var excelList : MutableList<ExcelData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateVoteThirdBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
    }

    private fun initButton() {
        binding.apply {
            val folder_black = getDrawable(requireContext(), R.drawable.folder_black)!!.constantState
            val bell_black = getDrawable(requireContext(), R.drawable.bell_black)!!.constantState
            val reward_black = getDrawable(requireContext(), R.drawable.reward_black)!!.constantState

            cvThirdListTv.setOnClickListener {
                if(cvThirdListIv.drawable.constantState!! == folder_black){

                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    val mimetypes = arrayOf(
                        "application/vnd.ms-excel",  // .xls
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" // .xlsx
                    )
                    intent.type = "*/*"
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
                    startActivityForResult(intent, FILE_REQUEST_CODE)

                }else{
                    cvThirdListIv.setImageResource(R.drawable.folder_black)
                    cvThirdListTitleTv.visibility = View.GONE
                    cvThirdListTv.setBackgroundResource(R.drawable.gray_color_box_5dp)
                    cvThirdListTv.setTextColor(resources.getColor(R.color.black,null))
                }
            }
            cvThirdAlarmTv.setOnClickListener {
                if(cvThirdAlarmIv.drawable.constantState!! == bell_black){
                    cvThirdAlarmIv.setImageResource(R.drawable.bell_blue)
                    cvThirdAlarmTv.setBackgroundResource(R.drawable.main_stroke_sub1_solid_box_5dp)
                    cvThirdAlarmTv.setTextColor(resources.getColor(R.color.main,null))
                }else{
                    cvThirdAlarmIv.setImageResource(R.drawable.bell_black)
                    cvThirdAlarmTv.setBackgroundResource(R.drawable.gray_color_box_5dp)
                    cvThirdAlarmTv.setTextColor(resources.getColor(R.color.black,null))
                }
            }
            cvThirdRewardTv.setOnClickListener {
                if(cvThirdRewardIv.drawable.constantState == reward_black) {
                    cvThirdRewardIv.setImageResource(R.drawable.reward_blue)
                    cvThirdRewardTv.setBackgroundResource(R.drawable.main_stroke_sub1_solid_box_5dp)
                    cvThirdRewardTv.setTextColor(resources.getColor(R.color.main,null))
                }else{
                    cvThirdRewardIv.setImageResource(R.drawable.reward_black)
                    cvThirdRewardTv.setBackgroundResource(R.drawable.gray_color_box_5dp)
                    cvThirdRewardTv.setTextColor(resources.getColor(R.color.black,null))
                }
            }
        }
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val uri: Uri? = data.data
                Log.e("uri", uri.toString())
                try{
                    val inputStream: InputStream? = context?.contentResolver?.openInputStream(uri!!)
                    getFileName(uri!!)
                    if (inputStream != null) {
                        readExcelFile(inputStream)
                    }
                }catch (e : FileNotFoundException) {
                    e.printStackTrace();
                }
                changeListBlue()
            }
        }
    }

    //xlsx도 포함 -> 권한 등에 오류 발생
//    private fun readExcelFile(inputStream : InputStream){
//        Log.e("1","여기")
//        try{
//            val myFileSystem = POIFSFileSystem(inputStream)
//            //워크 북
//            val myWorkBook = HSSFWorkbook(myFileSystem)
//            // 워크북에서 시트 가져오기
//            val s = myWorkBook.getSheetAt(0)
//
//            val sheet : HSSFSheet? = myWorkBook.getSheetAt(0)
//            Log.e("2","여기")
//            if(sheet != null){
//                Log.e("3","여기")
//                //행을 반복할 변수 만들어주기
//                val rowIter = sheet.rowIterator()
//                //행 넘버 변수 만들기
//                var rowno = 0
//
//                while (rowIter.hasNext()) {
//                    val myRow = rowIter.next() as HSSFRow
//                    if (rowno != 0) {
//                        val cellIter = myRow.cellIterator()
//                        var colno = 0
//                        var name = ""
//                        var studentID = ""
//                        while (cellIter.hasNext()) {
//                            val myCell = cellIter.next() as HSSFCell
//                            if (colno == 1) {//2번째 열이라면,
//                                name = myCell.toString()
//                            } else if (colno == 2) {//3번째 열이라면,
//                                studentID = myCell.toString()
//                            }
//                            colno++
//                        }
//                        Log.e("아이템",name+studentID)
//                        excelList.add(ExcelData(name, studentID))
//                    }
//                    rowno++
//                }
//                Log.e("checking", " items: $excelList");
//            }
//
//        } catch (e: Exception) {
//            Toast.makeText(context, "에러 발생", Toast.LENGTH_LONG).show()
//        }
//    }


    private fun readExcelFile(inputStream: InputStream) {
        try {
            val wb: Workbook = Workbook.getWorkbook(inputStream)
            val sheet: Sheet = wb.getSheet(0)
            val colTotal: Int = sheet.columns
            val rowIndexStart = 0
            val rowTotal: Int = sheet.getColumn(colTotal - 1).size

            for (row in rowIndexStart until rowTotal) {
                var name = ""
                var studentId = ""
                for (col in 0 until colTotal) {
                    val contents: String = sheet.getCell(col, row).contents
                    when (col) {
                        0 -> name = contents
                        1 -> studentId = contents
                    }
                }
                excelList.add(ExcelData(name,studentId))
            }
            Log.d("끝", excelList.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: BiffException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("Range")
    fun getFileName(uri: Uri) {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = context?.contentResolver?.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.lastPathSegment
        }
        filename = result
        Log.e("getFileNAME",result.toString())
    }

    private fun changeListBlue(){
        if(filename != null) {
            binding.apply {
                cvThirdListIv.setImageResource(R.drawable.folder_blue)
                cvThirdListTv.setBackgroundResource(R.drawable.main_stroke_sub1_solid_box_5dp)
                cvThirdListTv.setTextColor(resources.getColor(R.color.main,null))
                cvThirdListTitleTv.visibility = View.VISIBLE
                cvThirdListTitleTv.text = filename
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}