package com.AligatorAPT.DuckBox.view.activity

import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.AligatorAPT.DuckBox.databinding.ActivityGroupUpdateBinding
import com.AligatorAPT.DuckBox.view.data.MyGroupData
import java.lang.Exception

class GroupUpdateActivity : AppCompatActivity() {
    lateinit var binding: ActivityGroupUpdateBinding

    private var _groupDescription = "2022 건국대학교 총학생회입니다."
    lateinit var groupData: MyGroupData

    private val BACKGROUND_IMAGE = 100
    private val CIRCLE_IMAGE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        groupData = intent.getSerializableExtra("groupData") as MyGroupData

        binding.apply {
            //그룹 정보 추가 (추후 서버 연동에 따라 변경 가능)
            groupDescriptionEditText.setText(_groupDescription)
            groupImage.setImageResource(groupData.image)

            //버튼 이벤트
            backBtn.setOnClickListener {
                onBackPressed()
            }
            groupBackgroundBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                startActivityForResult(intent, BACKGROUND_IMAGE)
            }
            groupImageBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                startActivityForResult(intent, CIRCLE_IMAGE)
            }
            submitBtn.setOnClickListener {

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BACKGROUND_IMAGE || requestCode == CIRCLE_IMAGE) {
            if (resultCode == RESULT_OK) {
                val currentImageUri = data?.data
                try{
                    currentImageUri?.let {
                        if(Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                this.contentResolver,
                                currentImageUri
                            )
                            if(requestCode == BACKGROUND_IMAGE)
                                binding.groupBackground.setImageBitmap(bitmap)
                            else if (requestCode == CIRCLE_IMAGE)
                                binding.groupImage.setImageBitmap(bitmap)
                        } else {
                            val source = ImageDecoder.createSource(this.contentResolver, currentImageUri)
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            if(requestCode == BACKGROUND_IMAGE)
                                binding.groupBackground.setImageBitmap(bitmap)
                            else if (requestCode == CIRCLE_IMAGE)
                                binding.groupImage.setImageBitmap(bitmap)
                        }
                    }
                }catch(e: Exception)
                {
                    e.printStackTrace()
                }
            }else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show()
            }
        }
    }
}
