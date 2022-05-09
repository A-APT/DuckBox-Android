package com.AligatorAPT.DuckBox.view.fragment.navigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.AligatorAPT.DuckBox.databinding.FragmentMyBinding
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import com.AligatorAPT.DuckBox.view.activity.*
import com.AligatorAPT.DuckBox.view.dialog.ModalDialog

class MyFragment : Fragment() {
    private var _binding: FragmentMyBinding? = null
    private val binding: FragmentMyBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        val mActivity = activity as NavigationActivity

        binding.apply {
            email.text = MyApplication.prefs.getString("email", "notExist")
            nickname.text = MyApplication.prefs.getString("nickname","notExist")

            myPaper.setOnClickListener {
                val intent = Intent(mActivity, MyPaperActivity::class.java)
                startActivity(intent)
            }

            changeInfo.setOnClickListener {
                val intent = Intent(mActivity, ChangeInfoActivity::class.java)
                startActivity(intent)
            }

            termsOfUseBtn.setOnClickListener {
                val intent = Intent(mActivity, PolicyActivity::class.java)
                intent.putExtra("isTerm", true)
                startActivity(intent)
            }

            privacyPolicyBtn.setOnClickListener {
                val intent = Intent(mActivity, PolicyActivity::class.java)
                intent.putExtra("isTerm", false)
                startActivity(intent)
            }

            askMaster.setOnClickListener {
                val intent = Intent(mActivity, AskMasterActivity::class.java)
                startActivity(intent)
            }

            logout.setOnClickListener {
                //다이얼로그
                val bundle = Bundle()
                bundle.putString("message", "로그아웃 하시겠습니까?\n 데이터는 해당 계정에 저장됩니다.")
                val modalDialog = ModalDialog()
                modalDialog.arguments = bundle
                modalDialog.itemClickListener = object : ModalDialog.OnItemClickListener{
                    override fun OnPositiveClick() {
                        modalDialog.dismiss()
                    }

                    override fun OnNegativeClick() {
                        modalDialog.dismiss()
                    }
                }
                modalDialog.show(mActivity.supportFragmentManager, "ModalDialog")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
