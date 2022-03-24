package com.AligatorAPT.DuckBox.view.fragment.creategroup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentCreateGroupInfoBinding
import com.AligatorAPT.DuckBox.view.activity.CreateGroupActivity
import com.AligatorAPT.DuckBox.viewmodel.createvote.CreateGroupViewModel

class CreateGroupInfoFragment : Fragment() {
    private var _binding: FragmentCreateGroupInfoBinding? = null
    private val binding: FragmentCreateGroupInfoBinding get() = _binding!!
    private var checkValidation = booleanArrayOf(false, false)
    private var isActivateBtn = false

    private val model: CreateGroupViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateGroupInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun checkDuplicateGroupName():Boolean{
        val isDuplicate = false //추후 서버에 요청할 것

        if(isDuplicate){
            binding.errorGroupName.visibility  = View.VISIBLE
        }else{
            binding.errorGroupName.visibility  = View.INVISIBLE
        }

        return isDuplicate
    }

    private fun setIsActivateBtn(){
        val mActivity = activity as CreateGroupActivity
        binding.apply {
            if(checkValidation[0] && checkValidation[1]){
                binding.nextBtn.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.main))
                isActivateBtn = true
            }else{
                binding.nextBtn.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.darkgray))
                isActivateBtn = false
            }
        }
    }

    private fun init(){
        val mActivity = activity as CreateGroupActivity
        binding.apply {
            //입력값 빈칸 확인
            setGroupName.doAfterTextChanged {
                checkValidation[0] = setGroupName.text.toString() != ""
                setIsActivateBtn()
            }

            setIntroduce.doAfterTextChanged {
                checkValidation[1] = setIntroduce.text.toString() != ""
                setIsActivateBtn()
            }

            //버튼 이벤트
            nextBtn.setOnClickListener {
                if(isActivateBtn){
                    if(!checkDuplicateGroupName()){
                        model.setLeaderDid("groupLeaderDid")
                        model.setGroupInfo(setGroupName.text.toString(), setIntroduce.text.toString())
                        mActivity.changeFragment(CreateGroupImageFragment(), "이미지 추가하기")
                    }
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
