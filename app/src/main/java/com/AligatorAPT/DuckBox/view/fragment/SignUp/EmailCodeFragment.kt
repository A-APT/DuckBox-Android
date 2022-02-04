package com.AligatorAPT.DuckBox.view.fragment.SignUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentEmailBinding
import com.AligatorAPT.DuckBox.databinding.FragmentEmailCodeBinding

class EmailCodeFragment : Fragment() {
    private var _binding: FragmentEmailCodeBinding? = null
    private val binding: FragmentEmailCodeBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmailCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}