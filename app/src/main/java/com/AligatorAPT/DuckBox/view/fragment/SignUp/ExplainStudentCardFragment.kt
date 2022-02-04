package com.AligatorAPT.DuckBox.view.fragment.SignUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.AligatorAPT.DuckBox.databinding.FragmentExplainStudentCardBinding
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity

class ExplainStudentCardFragment : Fragment() {
    private var _binding: FragmentExplainStudentCardBinding? = null
    private val binding: FragmentExplainStudentCardBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExplainStudentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        val mActivity = activity as SignUpActivity
        binding.shootBtn.setOnClickListener {
            mActivity.changeFragment(StudentCardOutputFragment())
        }
    }
}