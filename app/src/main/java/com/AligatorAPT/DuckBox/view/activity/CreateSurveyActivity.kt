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
import com.AligatorAPT.DuckBox.databinding.ActivitySurveyCreateBinding
import com.AligatorAPT.DuckBox.dto.paper.Question
import com.AligatorAPT.DuckBox.dto.paper.SurveyRegisterDto
import com.AligatorAPT.DuckBox.retrofit.callback.RegisterCallBack
import com.AligatorAPT.DuckBox.view.adapter.createsurvey.CreateSurveyPagerAdapter
import com.AligatorAPT.DuckBox.view.fragment.createvote.CreateVoteFinalFragment
import com.AligatorAPT.DuckBox.viewmodel.CreateVoteViewModel
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import kotlin.collections.ArrayList

class CreateSurveyActivity : AppCompatActivity() {
    lateinit var binding: ActivitySurveyCreateBinding
    lateinit var viewPager : ViewPager2
    var checkValidation = booleanArrayOf(false,false,true)
    val viewModel : CreateVoteViewModel by viewModels()
    var surveyRegisterDto = SurveyRegisterDto(
            title = "",
            content = "",
            isGroup = false,
            groupId = "",
            startTime = Date(),
            finishTime = Date(),
            images = ArrayList<ByteArray>(),
            ownerPrivate = "",
            questions = ArrayList<Question>(),
            targets = ArrayList<Int>(),
            reward = false,
            notice = false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isVote.value = false
        binding.createSurveyLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.create))

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
        binding.createSurveyNextTv.setOnClickListener {

            if(viewPager.currentItem == 2){
                val blindsig = BlindSecp256k1()
                val keyPair = blindsig.generateKeyPair()

                viewModel.ownerPrivate.value = keyPair.privateKey.toString()
                viewModel.registerSurvey(object: RegisterCallBack {
                    override fun registerCallBack(flag: Boolean, id:String?) {
                        if(flag){
                            binding.createSurveyFr.visibility = View.VISIBLE
                            binding.createSurveyVp.visibility = View.GONE
                            binding.createSurveyNextTv.visibility = View.GONE
                            binding.createSurveyTl.visibility = View.GONE
                            binding.createSurveyTitle.text = "설문 생성 완료"

                            val fragment = CreateVoteFinalFragment()
                            val bundle = Bundle()
                            bundle.putBoolean("isVote",viewModel.isVote.value!!)
                            fragment.arguments = bundle
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.create_survey_fr, fragment)
                                .commit()
                        }
                    }
                })
            }
            viewPager.currentItem = viewPager.currentItem+1
            if(viewPager.currentItem == 2) binding.createSurveyNextTv.text = "완료"
            else binding.createSurveyNextTv.text = "다음"

            if(checkValidation[viewPager.currentItem]){
                binding.createSurveyNextTv.setBackgroundColor(ContextCompat.getColor(this,R.color.main))
                binding.createSurveyNextTv.isEnabled = true
            }else{
                binding.createSurveyNextTv.setBackgroundColor(ContextCompat.getColor(this, R.color.darkgray))
                binding.createSurveyNextTv.isEnabled = false
            }
        }
        binding.createSurveyCloseIv.setOnClickListener {
            finish()
        }
    }

    private fun initViewPager() {
        viewPager = binding.createSurveyVp
        viewPager.isUserInputEnabled = false
        viewPager.adapter = CreateSurveyPagerAdapter(this)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val tablayout = binding.createSurveyTl
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
            if(viewPager.currentItem != 2) binding.createSurveyNextTv.text = "다음"
            if(checkValidation[viewPager.currentItem]){
                binding.createSurveyNextTv.setBackgroundColor(ContextCompat.getColor(this,R.color.main))
                binding.createSurveyNextTv.isEnabled = true
            }else{
                binding.createSurveyNextTv.setBackgroundColor(ContextCompat.getColor(this, R.color.darkgray))
                binding.createSurveyNextTv.isEnabled = false
            }
        } else{
            super.onBackPressed()
        }
    }
}
