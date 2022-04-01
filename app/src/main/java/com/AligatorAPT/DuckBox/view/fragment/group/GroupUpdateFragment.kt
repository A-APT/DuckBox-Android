package com.AligatorAPT.DuckBox.view.fragment.group

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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.AligatorAPT.DuckBox.databinding.FragmentGroupUpdateBinding
import com.AligatorAPT.DuckBox.view.activity.GroupActivity
import com.AligatorAPT.DuckBox.viewmodel.GroupViewModel
import java.lang.Exception

class GroupUpdateFragment : Fragment() {
    private var _binding: FragmentGroupUpdateBinding? = null
    private val binding: FragmentGroupUpdateBinding get() = _binding!!

    private val model: GroupViewModel by activityViewModels()

    private val BACKGROUND_IMAGE = 100
    private val CIRCLE_IMAGE = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init(){
        val mActivity = activity as GroupActivity

        binding.apply {
            //그룹 정보 추가 (추후 서버 연동에 따라 변경 가능)
            model.description.observe(viewLifecycleOwner, Observer {
                groupDescriptionEditText.setText(it)
            })

            //이미지

            //버튼 이벤트
            backBtn.setOnClickListener {
                mActivity.onBackPressed()
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
        val mActivity = activity as GroupActivity

        if (requestCode == BACKGROUND_IMAGE || requestCode == CIRCLE_IMAGE) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val currentImageUri = data?.data
                try{
                    currentImageUri?.let {
                        if(Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                mActivity.contentResolver,
                                currentImageUri
                            )
                            if(requestCode == BACKGROUND_IMAGE)
                                binding.groupBackground.setImageBitmap(bitmap)
                            else if (requestCode == CIRCLE_IMAGE)
                                binding.groupImage.setImageBitmap(bitmap)
                        } else {
                            val source = ImageDecoder.createSource(mActivity.contentResolver, currentImageUri)
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
