package com.AligatorAPT.DuckBox.view.fragment.group

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentGroupUpdateBinding
import com.AligatorAPT.DuckBox.retrofit.callback.ApiCallback
import com.AligatorAPT.DuckBox.view.activity.GroupActivity
import com.AligatorAPT.DuckBox.viewmodel.GroupViewModel
import java.io.ByteArrayOutputStream
import java.lang.Exception

class GroupUpdateFragment : Fragment() {
    private var _binding: FragmentGroupUpdateBinding? = null
    private val binding: FragmentGroupUpdateBinding get() = _binding!!

    private val model: GroupViewModel by activityViewModels()

    private val BACKGROUND_IMAGE = 100
    private val CIRCLE_IMAGE = 101

    private var oldDescription = ""
    private var oldProfile: ByteArray? = null
    private var oldHeader: ByteArray? = null

    private var newProfile: Bitmap? = null
    private var newHeader: Bitmap? = null

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
                oldDescription = it
                groupDescriptionEditText.setText(it)
            })

            //이미지
            model.profile.observe(viewLifecycleOwner, Observer {
                if(it != null){
                    oldProfile = it

                    val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                    groupImage.setImageBitmap(bmp)
                    newProfile = bmp
                }else{
                    val bmp = BitmapFactory.decodeResource(mActivity.resources, R.drawable.sub2_color_box_3dp)
                    groupImage.setImageBitmap(bmp)
                }
            })

            model.header.observe(viewLifecycleOwner, Observer {
                if(it != null){
                    oldHeader = it

                    val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                    groupBackground.setImageBitmap(bmp)
                    newHeader = bmp

                }else{
                    val bmp = BitmapFactory.decodeResource(mActivity.resources, R.drawable.sub1_color_box_5dp)
                    groupBackground.setImageBitmap(bmp)
                }
            })

            //버튼 이벤트
            backBtn.setOnClickListener {
                //뒤로갈 때 (업데이트를 안할 때, 원래 정보로 돌려두기)
                model.updateGroupInfo(
                    _description = oldDescription,
                    _header = oldHeader,
                    _profile = oldProfile
                )
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
                val profileByteArray: ByteArrayOutputStream? = ByteArrayOutputStream()
                newProfile?.compress(Bitmap.CompressFormat.PNG, 2, profileByteArray)

                val headerByteArray: ByteArrayOutputStream? = ByteArrayOutputStream()
                newHeader?.compress(Bitmap.CompressFormat.PNG, 2, headerByteArray)

                model.updateGroupInfo(
                    _description = groupDescriptionEditText.text.toString(),
                    _profile = profileByteArray?.toByteArray(),
                    _header = headerByteArray?.toByteArray(),
                )

                model.updateGroup(object :ApiCallback{
                    override fun apiCallback(flag: Boolean) {
                        if(flag){
                            mActivity.changeFragment(GroupSettingFragment())
                        }else{
                            //업데이트를 안할 때, 원래 정보로 돌려두기
                            model.updateGroupInfo(
                                _description = oldDescription,
                                _header = oldHeader,
                                _profile = oldProfile
                            )
                            Toast.makeText(mActivity, "그룹 정보 업데이트를 다시 시도해주세요.", Toast.LENGTH_LONG).show()
                        }
                    }
                })
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
                            if(requestCode == BACKGROUND_IMAGE){
                                binding.groupBackground.setImageBitmap(bitmap)
                                newHeader = bitmap
                            }else if (requestCode == CIRCLE_IMAGE){
                                binding.groupImage.setImageBitmap(bitmap)
                                newProfile = bitmap
                            }
                        } else {
                            val source = ImageDecoder.createSource(mActivity.contentResolver, currentImageUri)
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            if(requestCode == BACKGROUND_IMAGE){
                                binding.groupBackground.setImageBitmap(bitmap)
                                newHeader = bitmap
                            }else if (requestCode == CIRCLE_IMAGE){
                                binding.groupImage.setImageBitmap(bitmap)
                                newProfile = bitmap
                            }
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
