package com.AligatorAPT.DuckBox.view.activity

import BlindSecp256k1
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityPollCreateBinding
import com.AligatorAPT.DuckBox.dto.vote.SurveyRegisterDto
import com.AligatorAPT.DuckBox.retrofit.callback.RegisterCallBack
import com.AligatorAPT.DuckBox.view.adapter.createsurvey.CreateSurveyPagerAdapter
import com.AligatorAPT.DuckBox.view.fragment.createvote.CreateVoteFinalFragment
import com.AligatorAPT.DuckBox.viewmodel.CreateVoteViewModel
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import kotlin.collections.ArrayList

class CreateSurveyActivity : AppCompatActivity() {
    lateinit var binding: ActivityPollCreateBinding
    lateinit var viewPager : ViewPager2
    var checkValidation = booleanArrayOf(false,false,true)
    val viewModel : CreateVoteViewModel by viewModels()
    var surveyRegisterDto = SurveyRegisterDto(
        "","",false,"", Date(), Date(),ArrayList<ByteArray>(), "", ArrayList(),ArrayList(),false,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPollCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isVote.value = false
        binding.createPollLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.create))

        viewModel.isVote.value = false
        surveyRegisterDto.isGroup = intent.getBooleanExtra("isGroup",false)
        if(surveyRegisterDto.isGroup){
            viewModel.setGroup(surveyRegisterDto.isGroup, intent.getStringExtra("groupId"))
        }else {
            viewModel.setGroup(surveyRegisterDto.isGroup, null)
        }

        initViewPager()

        initButton()
    }

    private fun initButton() {
        binding.createPollNextTv.setOnClickListener {

            if(viewPager.currentItem == 2){
                val blindsig = BlindSecp256k1()
                val keyPair = blindsig.generateKeyPair()

                viewModel.ownerPrivate.value = keyPair.privateKey.toString()
                viewModel.registerSurvey(object: RegisterCallBack {
                    override fun registerCallBack(flag: Boolean, id:String?) {
                        if(flag){
                            binding.createPollFr.visibility = View.VISIBLE
                            binding.createPollVp.visibility = View.GONE
                            binding.createPollNextTv.visibility = View.GONE
                            binding.createPollTl.visibility = View.GONE
                            binding.createPollTitle.text = "설문 생성 완료"

                            val fragment = CreateVoteFinalFragment()
                            val bundle = Bundle()
                            bundle.putBoolean("isVote",viewModel.isVote.value!!)
                            fragment.arguments = bundle
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.create_poll_fr, fragment)
                                .commit()
                        }
                    }
                })
            }
            viewPager.currentItem = viewPager.currentItem+1
            if(viewPager.currentItem == 2) binding.createPollNextTv.text = "완료"
            else binding.createPollNextTv.text = "다음"

            if(checkValidation[viewPager.currentItem]){
                binding.createPollNextTv.setBackgroundColor(ContextCompat.getColor(this,R.color.main))
                binding.createPollNextTv.isEnabled = true
            }else{
                binding.createPollNextTv.setBackgroundColor(ContextCompat.getColor(this, R.color.darkgray))
                binding.createPollNextTv.isEnabled = false
            }
            binding.createPollCloseIv.setOnClickListener {
                finish()
            }
        }
    }

    private fun initViewPager() {
        viewPager = binding.createPollVp
        viewPager.isUserInputEnabled = false
        viewPager.adapter = CreateSurveyPagerAdapter(this)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val tablayout = binding.createPollTl
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
            if(viewPager.currentItem != 2) binding.createPollNextTv.text = "다음"
            if(checkValidation[viewPager.currentItem]){
                binding.createPollNextTv.setBackgroundColor(ContextCompat.getColor(this,R.color.main))
                binding.createPollNextTv.isEnabled = true
            }else{
                binding.createPollNextTv.setBackgroundColor(ContextCompat.getColor(this, R.color.darkgray))
                binding.createPollNextTv.isEnabled = false
            }
        } else{
            super.onBackPressed()
        }
    }
}
