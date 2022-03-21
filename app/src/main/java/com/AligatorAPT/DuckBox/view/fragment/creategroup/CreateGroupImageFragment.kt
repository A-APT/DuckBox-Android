package com.AligatorAPT.DuckBox.view.fragment.creategroup

import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.AligatorAPT.DuckBox.databinding.FragmentCreateGroupImageBinding
import com.AligatorAPT.DuckBox.view.activity.CreateGroupActivity
import java.lang.Exception

class CreateGroupImageFragment : Fragment() {
    private var _binding: FragmentCreateGroupImageBinding? = null
    private val binding: FragmentCreateGroupImageBinding get() = _binding!!

    private val CREATE_BACKGROUND_IMAGE = 200
    private val CREATE_CIRCLE_IMAGE = 201

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateGroupImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init(){
        binding.apply {
            val mActivity = activity as CreateGroupActivity

            //버튼 이벤트
            groupBackgroundBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                startActivityForResult(intent, CREATE_BACKGROUND_IMAGE)
            }

            groupImageBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                startActivityForResult(intent, CREATE_CIRCLE_IMAGE)
            }

            nextBtn.setOnClickListener {
                mActivity.changeFragment(FinishCreateGroupFragment(), "그룹 만들기 완료")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val mActivity = activity as CreateGroupActivity

        if(requestCode == CREATE_BACKGROUND_IMAGE || requestCode == CREATE_CIRCLE_IMAGE){
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val currentImageUri = data?.data
                try{
                    currentImageUri?.let {
                        if(Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                mActivity.contentResolver,
                                currentImageUri
                            )
                            if(requestCode == CREATE_BACKGROUND_IMAGE)
                                binding.groupBackground.setImageBitmap(bitmap)
                            else if (requestCode == CREATE_CIRCLE_IMAGE)
                                binding.groupImage.setImageBitmap(bitmap)
                        } else {
                            val source = ImageDecoder.createSource(mActivity.contentResolver, currentImageUri)
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            if(requestCode == CREATE_BACKGROUND_IMAGE)
                                binding.groupBackground.setImageBitmap(bitmap)
                            else if (requestCode == CREATE_CIRCLE_IMAGE)
                                binding.groupImage.setImageBitmap(bitmap)
                        }
                    }
                }catch(e: Exception)
                {
                    e.printStackTrace()
                }
            }else if(resultCode == AppCompatActivity.RESULT_CANCELED)
            {
                Toast.makeText(mActivity, "사진 선택 취소", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
