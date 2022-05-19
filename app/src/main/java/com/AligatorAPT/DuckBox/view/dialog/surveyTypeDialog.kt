package com.AligatorAPT.DuckBox.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.AligatorAPT.DuckBox.databinding.DialogSurveyOptionBinding

class surveyTypeDialog: DialogFragment() {
    private var _binding: DialogSurveyOptionBinding ?= null
    private val binding: DialogSurveyOptionBinding get() = _binding!!

    interface surveyDialogListener {
        fun onDialogMultipleClick()
        fun onDialogLinearClick()
    }

    var surveyListner: surveyDialogListener ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSurveyOptionBinding.inflate(inflater, container, false)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            dialogSurveyMultiple.setOnClickListener {
                surveyListner?.onDialogMultipleClick()
                dismiss()
            }
            dialogSurveyLinear.setOnClickListener {
                surveyListner?.onDialogLinearClick()
                dismiss()
            }
            dialogSurveyClose.setOnClickListener {
                dismiss()
            }
        }
    }
}