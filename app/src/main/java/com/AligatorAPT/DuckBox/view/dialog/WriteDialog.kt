package com.AligatorAPT.DuckBox.view.dialog

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.AligatorAPT.DuckBox.databinding.DialogWriteBinding
import com.AligatorAPT.DuckBox.view.activity.CreateSurveyActivity
import android.util.DisplayMetrics
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity

class WriteDialog: DialogFragment() {
    private var _binding: DialogWriteBinding? = null
    private val binding: DialogWriteBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            val args = arguments
            val isGroup = args!!.getBoolean("isGroup")
            val groupId = args.getString("groupId")

            voteBtn.setOnClickListener {
                val intent = Intent(requireActivity(), CreateVoteActivity::class.java)
                intent.putExtra("isGroup",isGroup)
                intent.putExtra("groupId",groupId)
                startActivity(intent)
                dismiss()
            }
            pollBtn.setOnClickListener {
                val intent = Intent(requireActivity(), CreateSurveyActivity::class.java)
                intent.putExtra("isGroup",isGroup)
                intent.putExtra("groupId",groupId)
                startActivity(intent)
                dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //화면 크기 가져오기
        val display = activity?.windowManager?.defaultDisplay
        val outMetrics = DisplayMetrics()
        display?.getMetrics(outMetrics)

        val density = resources.displayMetrics.density
        val dpHeight = outMetrics.heightPixels / density
        val dpWidth = outMetrics.widthPixels / density

        //다이얼로그 위치 선정
        val params = dialog?.window?.attributes
        params?.y = (dpHeight - 130.0).toInt()
        params?.x = (dpWidth - 20.0).toInt()

        dialog!!.window!!.attributes = params
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
