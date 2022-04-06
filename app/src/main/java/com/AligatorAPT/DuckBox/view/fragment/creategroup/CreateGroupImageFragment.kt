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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.AligatorAPT.DuckBox.databinding.FragmentCreateGroupImageBinding
import com.AligatorAPT.DuckBox.retrofit.callback.ApiCallback
import com.AligatorAPT.DuckBox.view.activity.CreateGroupActivity
import com.AligatorAPT.DuckBox.viewmodel.createvote.CreateGroupViewModel
import java.lang.Exception

class CreateGroupImageFragment : Fragment() {
    private var _binding: FragmentCreateGroupImageBinding? = null
    private val binding: FragmentCreateGroupImageBinding get() = _binding!!

    private val CREATE_BACKGROUND_IMAGE = 200
    private val CREATE_CIRCLE_IMAGE = 201

    private val model: CreateGroupViewModel by activityViewModels()
    private lateinit var mActivity: CreateGroupActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateGroupImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //뒤로가기 했을 때, 이미지 설정
        model.header.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.groupBackground.setImageBitmap(it)
            }
        })

        model.profile.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.groupImage.setImageBitmap(it)
            }
        })

        init()
    }

    private fun init() {
        mActivity = activity as CreateGroupActivity

        binding.apply {
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
                model.register(
                   object : ApiCallback {
                        override fun apiCallback(flag: Boolean) {
                            if(flag){
                                //화면 전환
                                mActivity.changeFragment(FinishCreateGroupFragment(), "그룹 만들기 완료")
                            }
                        }
                    }
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val mActivity = activity as CreateGroupActivity

        if (requestCode == CREATE_BACKGROUND_IMAGE || requestCode == CREATE_CIRCLE_IMAGE) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val currentImageUri = data?.data
                try {
                    currentImageUri?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                mActivity.contentResolver,
                                currentImageUri
                            )
                            if (requestCode == CREATE_BACKGROUND_IMAGE) {
                                model.setGroupHeader(bitmap)
                                binding.groupBackground.setImageBitmap(bitmap)
                            } else if (requestCode == CREATE_CIRCLE_IMAGE) {
                                model.setGroupProfile(bitmap)
                                binding.groupImage.setImageBitmap(bitmap)
                            }
                        } else {
                            val source =
                                ImageDecoder.createSource(
                                    mActivity.contentResolver,
                                    currentImageUri
                                )
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            if (requestCode == CREATE_BACKGROUND_IMAGE) {
                                model.setGroupHeader(bitmap)
                                binding.groupBackground.setImageBitmap(bitmap)
                            } else if (requestCode == CREATE_CIRCLE_IMAGE) {
                                model.setGroupProfile(bitmap)
                                binding.groupImage.setImageBitmap(bitmap)
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else if (resultCode == AppCompatActivity.RESULT_CANCELED) {
                Toast.makeText(mActivity, "사진 선택 취소", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
