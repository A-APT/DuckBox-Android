package com.AligatorAPT.DuckBox.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityVotedetailImageinfoBinding
import com.AligatorAPT.DuckBox.view.adapter.BannerAdapter
import com.AligatorAPT.DuckBox.viewmodel.VoteViewModel

class VoteDetailImageInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityVotedetailImageinfoBinding
    private val voteModel = VoteViewModel.VoteSingletonGroup.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_votedetail_imageinfo)
        binding = ActivityVotedetailImageinfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    fun init(){
        binding.apply {

            val position = intent.getIntExtra("position",0)
            val img_arr = voteModel!!.myVote.value!![position].images as ArrayList<String>

            vdImageCloseIv.setOnClickListener { finish() }
            val imageAdapter = BannerAdapter(img_arr)
            imageAdapter.itemClickListener = object: BannerAdapter.OnItemClickListener{
                override fun OnItemClick(
                    data: String,
                    position: Int
                ) {
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