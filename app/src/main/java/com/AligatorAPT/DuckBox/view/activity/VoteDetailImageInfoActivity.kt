package com.AligatorAPT.DuckBox.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityVotedetailImageinfoBinding
import com.AligatorAPT.DuckBox.view.adapter.votedetail.VoteDetailImageAdapter

class VoteDetailImageInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityVotedetailImageinfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_votedetail_imageinfo)
        binding = ActivityVotedetailImageinfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    fun init(){
        binding.apply {
            val img_arr = intent.getSerializableExtra("img_arr") as ArrayList<Int>
            val position = intent.getIntExtra("position",0)
            vdImageCloseIv.setOnClickListener { finish() }
            val imageAdapter = VoteDetailImageAdapter(img_arr)
            imageAdapter.itemClickListener = object: VoteDetailImageAdapter.OnItemClickListener{
                override fun OnClick(position: Int) {
                }
            }
            vdImageVp.adapter = imageAdapter
            vdImageVp.setCurrentItem(position,true)
            binding.vdImageIndicatorTv.text = "${position+1} / ${img_arr.size}"

            binding.vdImageVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.e("POSITION",position.toString())
                    binding.vdImageIndicatorTv.text = "${position+1} / ${img_arr.size}"
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
            vdImageVp.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }
}