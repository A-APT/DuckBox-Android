package com.AligatorAPT.DuckBox.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.AligatorAPT.DuckBox.databinding.DialogModalBinding

class ModalDialog: DialogFragment() {
    private var _binding: DialogModalBinding? = null
    private val binding: DialogModalBinding get() = _binding!!

    private var _message = ""

    interface OnItemClickListener{
        fun OnPositiveClick()
        fun OnNegativeClick()
    }

    var itemClickListener: OnItemClickListener ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogModalBinding.inflate(inflater, container, false)

        _message = arguments?.getString("message").toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setMessage.text = _message
            negativeBtn.setOnClickListener {
                itemClickListener?.OnNegativeClick()
            }
            positiveBtn.setOnClickListener {
                itemClickListener?.OnPositiveClick()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
