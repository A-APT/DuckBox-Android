package com.AligatorAPT.DuckBox.view.fragment.creategroup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.AligatorAPT.DuckBox.databinding.FragmentFinishCreateGroupBinding
import com.AligatorAPT.DuckBox.view.activity.NavigationActivity
import com.AligatorAPT.DuckBox.viewmodel.createvote.CreateGroupViewModel

class FinishCreateGroupFragment : Fragment() {
    private var _binding: FragmentFinishCreateGroupBinding? = null
    private val binding: FragmentFinishCreateGroupBinding get() = _binding!!

    private val model: CreateGroupViewModel by activityViewModels()
    private var groupTitle = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinishCreateGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        binding.apply {
            //그룹 명
            model.name.observe(viewLifecycleOwner, Observer{
                finishGroupName.text = it
                groupTitle = it
            })

            //버튼 이벤트
            startGroupDetail.setOnClickListener {
                //홈으로 이등
                val intent = Intent(activity, NavigationActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
