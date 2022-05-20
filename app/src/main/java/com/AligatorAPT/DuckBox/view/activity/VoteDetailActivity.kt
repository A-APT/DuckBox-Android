package com.AligatorAPT.DuckBox.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityVoteDetailBinding
import com.AligatorAPT.DuckBox.dto.paper.VoteDetailDto
import com.AligatorAPT.DuckBox.view.adapter.BannerAdapter
import com.AligatorAPT.DuckBox.view.adapter.VoteDetailListAdapter
import com.AligatorAPT.DuckBox.viewmodel.VoteDetailViewModel
import com.AligatorAPT.DuckBox.viewmodel.VoteViewModel
import java.util.*

class VoteDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityVoteDetailBinding
    private var img_arr : ArrayList<String> = arrayListOf()
    private var candidate: ArrayList<String> = arrayListOf()
    private lateinit var ListAdapter : VoteDetailListAdapter
    private lateinit var voteList : VoteDetailDto
    private lateinit var time: String

    private val model: VoteDetailViewModel by viewModels()
    private val voteModel = VoteViewModel.VoteSingletonGroup.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote_detail)
        binding = ActivityVoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val position = intent.getIntExtra("position", 0)
        time = intent.getStringExtra("time").toString()
        voteList = voteModel!!.myVote.value!![position]
        img_arr = voteList.images as ArrayList<String>
        candidate = voteList.candidates as ArrayList<String>

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
        
        initToolbar()
        initText()
        initRV()
        initImageRV()
        initFinalBtn()
    }

    private fun initText() {
        binding.apply{
            vdTitleTv.text = voteList.title
            vdUserNameTv.text = voteList.owner
            vdContentTv.text = voteList.content
            vdLastTimeTv.text = time
        }
    }

    private fun initToolbar() {
        binding.vdToolbar.apply { 
            vdTooblarBackIv.setOnClickListener{
                onBackPressed()
            }
            vdToolbarShareTv.setOnClickListener {
                //공유하기 버튼 클릭
            }
        }
    }

    private fun initRV() {
        binding.apply {
            ListAdapter = VoteDetailListAdapter(candidate, model)
            vdListRv.adapter = ListAdapter
        }
    }

    private fun initImageRV() {
        val imageAdapter = BannerAdapter(img_arr)
        imageAdapter.itemClickListener = object: BannerAdapter.OnItemClickListener{
            override fun OnItemClick(
                holder: BannerAdapter.MyViewHolder,
                view: View,
                data: String,
                position: Int
            ) {
                val intent = Intent(applicationContext, VoteDetailImageInfoActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("img_arr",img_arr)
                intent.putExtra("position",position)
                startActivity(intent)
            }
        }
        binding.vdBannerVp.adapter = imageAdapter
        binding.vdIndicatorTv.text = ("${binding.vdBannerVp.currentItem+1} / ${img_arr.size}")

        binding.vdBannerVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.vdIndicatorTv.text = ("${position+1} / ${img_arr.size}")
            }
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
            override fun onPageScrolled(position: Int,
                                        positionOffset: Float,
                                        positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
        binding.vdBannerVp.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    @SuppressLint("ResourceAsColor")
    private fun initFinalBtn() {
        binding.vdFinalTv.setOnClickListener {
            //투표 완료
            binding.vdFinalTv.setBackgroundResource(R.color.darkgray)
            binding.vdFinalTv.setText("제출 완료")
            ListAdapter.isClickable = false
        }
    }
}
