package com.AligatorAPT.DuckBox.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityVoteDetailBinding
import com.AligatorAPT.DuckBox.view.adapter.VoteDetailListAdapter
import com.AligatorAPT.DuckBox.viewmodel.VoteDetailViewModel

class VoteDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityVoteDetailBinding
    private val imagearr : ArrayList<Int> = arrayListOf(R.drawable.banner1,R.drawable.banner2,R.drawable.banner2)
    private val arr: ArrayList<String> = arrayListOf("1. 절대 있을 수 없다..",
        "2. 있을 수 있다!", "3. 할 수 있다.!!", "4. 안녕안녕안녕", "5. 최대한 길게ㅔㅔ", "6. 난 중립이다.", "7. 하이하이하이하이", "8. 지금은 오후 8시")
    private lateinit var ListAdapter : VoteDetailListAdapter

    private val model: VoteDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote_detail)
        binding = ActivityVoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.isSelected.observe(this){
            if(it){
                Log.e("observe",model.isSelected.toString())
                binding.vdFinalTv.isEnabled = true
                binding.vdFinalTv.setBackgroundColor(ContextCompat.getColor(this,R.color.main))
            }else{
                binding.vdFinalTv.isEnabled = false
                binding.vdFinalTv.setBackgroundColor(ContextCompat.getColor(this,R.color.darkgray))
            }
        }

        initRV()
        initImageRV()
        initFinalBtn()
    }

    private fun initRV() {
        binding.apply {
            ListAdapter = VoteDetailListAdapter(arr, model)
            vdListRv.adapter = ListAdapter
        }
    }

    private fun initImageRV() {

    }

    private fun initFinalBtn() {
        binding.vdFinalTv.setOnClickListener {
            //투표 완료
            binding.vdFinalTv.visibility = View.GONE
            binding.vdListRv.isClickable = false
        }
    }
}
