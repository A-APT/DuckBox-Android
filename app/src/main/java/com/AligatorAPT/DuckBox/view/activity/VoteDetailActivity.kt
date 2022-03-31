package com.AligatorAPT.DuckBox.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityVoteDetailBinding
import com.AligatorAPT.DuckBox.view.adapter.VoteDetailListAdapter

class VoteDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityVoteDetailBinding
    private val imagearr : ArrayList<Int> = arrayListOf(R.drawable.banner1,R.drawable.banner2,R.drawable.banner2)
    private val arr: ArrayList<String> = arrayListOf("1. 절대 있을 수 없다..",
        "2. 있을 수 있다!", "3. 할 수 있다.!!", "4. 안녕안녕안녕", "5. 최대한 길게ㅔㅔ", "6. 난 중립이다.", "7. 하이하이하이하이", "8. 지금은 오후 8시")
    private lateinit var ListAdapter : VoteDetailListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote_detail)
        binding = ActivityVoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRV()
        initImageRV()
    }

    private fun initRV() {
        binding.apply {
            ListAdapter = VoteDetailListAdapter(arr)
            ListAdapter.itemClickListener = object: VoteDetailListAdapter.OnItemClickListener{
                override fun onTouch(
                    holder: VoteDetailListAdapter.ViewHolder,
                    view: View,
                    position: Int
                ) {
                    holder.binding.vdCheckIv.visibility = View.VISIBLE
                    holder.binding.vdTextCv.setBackgroundResource(R.drawable.main_stroke_sub1_solid_box_5dp)
                    holder.binding.vdCheckIv
                }
            }
            vdListRv.adapter = ListAdapter
        }
    }

    private fun initImageRV() {

    }

}
