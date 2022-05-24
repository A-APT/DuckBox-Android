package com.AligatorAPT.DuckBox.view.activity

import BlindSecp256k1
import BlindedData
import Point
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityVoteDetailBinding
import com.AligatorAPT.DuckBox.dto.paper.BallotStatus
import com.AligatorAPT.DuckBox.dto.paper.VoteDetailDto
import com.AligatorAPT.DuckBox.dto.user.BlindSigRequestDto
import com.AligatorAPT.DuckBox.dto.user.BlindSigToken
import com.AligatorAPT.DuckBox.ethereum.BallotContract
import com.AligatorAPT.DuckBox.ethereum.EthereumManagement
import com.AligatorAPT.DuckBox.retrofit.callback.TokenCallback
import com.AligatorAPT.DuckBox.view.adapter.BannerAdapter
import com.AligatorAPT.DuckBox.view.adapter.VoteDetailListAdapter
import com.AligatorAPT.DuckBox.view.adapter.VoteResultListAdapter
import com.AligatorAPT.DuckBox.viewmodel.VoteDetailViewModel
import com.AligatorAPT.DuckBox.viewmodel.VoteViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.web3j.crypto.Credentials
import java.math.BigInteger
import kotlin.collections.ArrayList

class VoteDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityVoteDetailBinding
    private var img_arr : ArrayList<String> = arrayListOf()
    private var candidate: ArrayList<String> = arrayListOf()
    private lateinit var ListAdapter : VoteDetailListAdapter
    private lateinit var voteList : VoteDetailDto
    private lateinit var time: String
    private lateinit var status: BallotStatus

    private val model: VoteDetailViewModel by viewModels()
    private val voteModel = VoteViewModel.VoteSingletonGroup.getInstance()
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO
    private lateinit var blindSecp256k1: BlindSecp256k1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote_detail)
        binding = ActivityVoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        blindSecp256k1 = BlindSecp256k1()
        val position = intent.getIntExtra("position", 0)
        time = intent.getStringExtra("time").toString()
        status = intent.getSerializableExtra("status") as BallotStatus

        voteList = voteModel!!.myVote.value!![position]
        img_arr = voteList.images as ArrayList<String>
        candidate = voteList.candidates as ArrayList<String>

        initToolbar()
        initText()
        initImageRV()

        Log.e("status_VoteDETAIL",status.toString())
        if(status == BallotStatus.CLOSE){
            binding.vdFinalTv.visibility = View.GONE
            initResultRV()
        }else{
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
            initFinalBtn()
        }

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

    private fun initResultRV() {
        binding.apply {
            CoroutineScope(dispatcher).launch{
                val arr_can: List<BigInteger> = BallotContract.resultOfBallot(ballotId = voteList.id)
                var max = 0
                var allCount = BigInteger("0")
                for(i in arr_can.indices){
                    allCount += arr_can[i]
                    if(arr_can[max] < arr_can[i]){
                        max = i
                    }
                }

                //디바이스 크기
                val windowManager = this@VoteDetailActivity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val display = windowManager.defaultDisplay
                val size = android.graphics.Point()
                display.getSize(size)
                val deviceWidth = size.x

                val voteResultAdapter = VoteResultListAdapter(candidate, arr_can, max, allCount, deviceWidth)
                vdListRv.adapter = voteResultAdapter
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
                data: String,
                position: Int
            ) {
                val intent = Intent(this@VoteDetailActivity, VoteDetailImageInfoActivity::class.java)
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
        binding.apply {
            vdFinalTv.setOnClickListener {
                //투표 완료
                val message = model.num.toString().encodeToByteArray()
                val R_ = Point(
                    BigInteger("d80387d2861da050c1a8ae11c9a1ef5ed93572bd6537d50984c1dea2f2db912b", 16),
                    BigInteger("edcef3840df9cd47256996c460f0ce045ccb4fac5e914f619c44ad642779011", 16)
                )
                val blindedData: BlindedData = blindSecp256k1.blind(R_,message)
                val blindSigRequestDto = BlindSigRequestDto(voteList.id, blindedData.blindM.toString(16))
                voteModel!!.generateVoteToken(blindSigRequestDto, object: TokenCallback {
                    override fun tokenCallback(flag: Boolean, _blindsigToken: BlindSigToken?) {
                        if(flag){
                            vdListLayout.visibility = View.GONE
                            vdAfterVoteLayout.visibility = View.VISIBLE
                            vdFinalTv.setBackgroundResource(R.color.darkgray)
                            vdFinalTv.setText("제출 완료")
                            ListAdapter.isClickable = false

                            CoroutineScope(dispatcher).launch{
                                val r = arrayListOf(blindedData.R.x,blindedData.R.y)
                                val serverBsig = BigInteger(_blindsigToken!!.serverBSig, 16)
                                val ownerBsig = BigInteger(_blindsigToken.ownerBSig, 16)
                                val serverSig = blindSecp256k1.unblind(blindedData.a, blindedData.b, serverBsig)
                                val voteOwnerSig = blindSecp256k1.unblind(blindedData.a, blindedData.b, ownerBsig)
                                // create new account
                                val pseudoCredentials: Credentials = EthereumManagement.createNewCredentials("PASSWORD") // TODO user password

                                BallotContract.vote(
                                    _ballotId = voteList.id,
                                    _m = model.num.toString(),
                                    _serverSig = serverSig,
                                    _ownerSig = voteOwnerSig,
                                    R = r,
                                    pseudoCredentials = pseudoCredentials
                                )
                            }
                        }
                    }
                })
            }
        }
    }
}
