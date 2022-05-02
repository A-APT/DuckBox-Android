package com.AligatorAPT.DuckBox.view.activity

import BlindSecp256k1
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.AligatorAPT.DuckBox.databinding.ActivityCreateVoteBinding
import com.AligatorAPT.DuckBox.view.adapter.createvote.CreateVotePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.view.fragment.createvote.CreateVoteFinalFragment
import androidx.activity.viewModels
import com.AligatorAPT.DuckBox.ethereum.BallotsContract
import com.AligatorAPT.DuckBox.ethereum.GanacheAddress
import com.AligatorAPT.DuckBox.retrofit.callback.RegisterCallBack
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import com.AligatorAPT.DuckBox.view.data.VoteRegisterDto
import com.AligatorAPT.DuckBox.viewmodel.CreateVoteViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class CreateVoteActivity : FragmentActivity() {
    lateinit var binding: ActivityCreateVoteBinding
    lateinit var viewPager : ViewPager2
    var checkValidation = booleanArrayOf(false,false,true)
    var voteRegisterDto = VoteRegisterDto(
        "","",false,"",Date(), Date(),ArrayList<ByteArray>(), "", ArrayList<String>(), null,false,false)

    val viewModel : CreateVoteViewModel by viewModels()
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateVoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        voteRegisterDto.isGroup = intent.getBooleanExtra("isGroup",false)
        if(voteRegisterDto.isGroup){
            viewModel.setGroup(voteRegisterDto.isGroup, intent.getStringExtra("groupId"))
        }else {
            viewModel.setGroup(voteRegisterDto.isGroup, null)
        }

        initViewPager()

        initButton()
    }

    private fun initButton() {
        binding.createVoteNextTv.setOnClickListener {

            if(viewPager.currentItem == 2) {
                val blindsig = BlindSecp256k1()
                val keyPair = blindsig.generateKeyPair()
                viewModel.ownerPrivate.value = keyPair.privateKey.toString()

                viewModel.registerVote(object: RegisterCallBack {
                    override fun registerCallBack(flag: Boolean, id:String?) {
                        if(flag){
                            binding.createVoteFr.visibility = View.VISIBLE
                            binding.createVoteVp.visibility = View.GONE
                            binding.createVoteNextTv.visibility = View.GONE
                            binding.createVoteTl.visibility = View.GONE
                            binding.createVoteTitle.text = "투표 생성 완료"

                            supportFragmentManager.beginTransaction()
                                .replace(R.id.create_vote_fr, CreateVoteFinalFragment())
                                .commit()
                        }
                    }
                })
            }

            viewPager.currentItem = viewPager.currentItem+1
            if(viewPager.currentItem == 2) binding.createVoteNextTv.text = "완료"
            else binding.createVoteNextTv.text = "다음"

            if(checkValidation[viewPager.currentItem]){
                binding.createVoteNextTv.setBackgroundColor(ContextCompat.getColor(this,R.color.main))
                binding.createVoteNextTv.isEnabled = true
            }else{
                binding.createVoteNextTv.setBackgroundColor(ContextCompat.getColor(this, R.color.darkgray))
                binding.createVoteNextTv.isEnabled = false
            }
        }

        binding.createVoteCloseIv.setOnClickListener {
            finish()
        }
    }

    private fun initViewPager() {
        viewPager = binding.createVoteVp
        viewPager.isUserInputEnabled = false
        viewPager.adapter = CreateVotePagerAdapter(this)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val tablayout = binding.createVoteTl
        TabLayoutMediator(tablayout, viewPager){ tab, position ->
        }.attach()

        val tabs = tablayout.getChildAt(0) as ViewGroup

        for (i in 0 until tabs.childCount) {
            val tab = tabs.getChildAt(i)
            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 0f
            layoutParams.marginEnd = 25
            layoutParams.width = 62
            tab.layoutParams = layoutParams
            tablayout.requestLayout()
            tab.setOnTouchListener( { v, event -> true })
        }
    }

    override fun onBackPressed() {
        val currentItem: Int = viewPager.currentItem
        if (currentItem != 0) {
            if(viewPager.visibility == View.GONE)finish()

            viewPager.setCurrentItem(currentItem - 1, true)
            if(viewPager.currentItem != 2) binding.createVoteNextTv.text = "다음"
            if(checkValidation[viewPager.currentItem]){
                binding.createVoteNextTv.setBackgroundColor(ContextCompat.getColor(this,R.color.main))
                binding.createVoteNextTv.isEnabled = true
            }else{
                binding.createVoteNextTv.setBackgroundColor(ContextCompat.getColor(this, R.color.darkgray))
                binding.createVoteNextTv.isEnabled = false
            }
        } else{
            super.onBackPressed()
        }
    }
}