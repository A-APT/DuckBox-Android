package com.AligatorAPT.DuckBox.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityVoteDetailBinding
import com.AligatorAPT.DuckBox.view.adapter.votedetail.VoteDetailImageAdapter
import com.AligatorAPT.DuckBox.view.adapter.votedetail.VoteDetailListAdapter
import com.AligatorAPT.DuckBox.viewmodel.VoteDetailViewModel
import java.util.*

class VoteDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityVoteDetailBinding
    private val img_arr : ArrayList<Int> = arrayListOf(R.drawable.banner1,R.drawable.banner2,R.drawable.banner3)
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
        
        initToolbar()
        initRV()
        initImageRV()
        initFinalBtn()
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
            ListAdapter = VoteDetailListAdapter(arr, model)
            vdListRv.adapter = ListAdapter
        }
    }

    private fun initImageRV() {
        val imageAdapter = VoteDetailImageAdapter(img_arr)
        imageAdapter.itemClickListener = object: VoteDetailImageAdapter.OnItemClickListener{
            override fun OnClick(position: Int) {
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
                Log.e("POSITION",position.toString())
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
