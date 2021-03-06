package com.AligatorAPT.DuckBox.view.fragment.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentCommunityBinding
import com.AligatorAPT.DuckBox.dto.paper.BallotStatus
import com.AligatorAPT.DuckBox.dto.paper.VoteDetailDto
import com.AligatorAPT.DuckBox.retrofit.callback.VoteCallback
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import com.AligatorAPT.DuckBox.view.activity.*
import com.AligatorAPT.DuckBox.view.adapter.BannerAdapter
import com.AligatorAPT.DuckBox.view.adapter.PaperListAdapter
import com.AligatorAPT.DuckBox.view.dialog.WriteDialog
import com.AligatorAPT.DuckBox.viewmodel.VoteViewModel
import kotlin.collections.ArrayList

class CommunityFragment : Fragment() {
    private var _binding: FragmentCommunityBinding? = null
    private val binding: FragmentCommunityBinding get() = _binding!!

    private lateinit var communityAdapter: PaperListAdapter
    private lateinit var bannerAdapter: BannerAdapter

    private var toggleFlag = false
    private val voteModel = VoteViewModel.VoteSingletonGroup.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        binding.apply {

            //투표 리스트 가져오기
            setVoteList(false)

            //배너
            bannerAdapter = BannerAdapter(arrayListOf("YW5kcm9pZC5ncmFwaGljcy5CaXRtYXBAYjk3MTQ2"))
            bannerAdapter.itemClickListener = object :BannerAdapter.OnItemClickListener{
                override fun OnItemClick(data: String, position: Int) {

                }
            }
            viewpagerBanner.adapter = bannerAdapter
            viewpagerBanner.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            //배너 indicator
            indicator.setViewPager(viewpagerBanner)

            //투표리스트 리사이클러뷰
            voteModel!!.myVote.observe(viewLifecycleOwner, androidx.lifecycle.Observer{
                if(it != null){
                    Log.e("COMMUNITY_OBSERVER",it.toString())
                    val arr = ArrayList<VoteDetailDto>()
                    arr.addAll(it)
                    communityAdapter = PaperListAdapter(arr)

                }else{
                    val arr = ArrayList<VoteDetailDto>()
                    communityAdapter = PaperListAdapter(arr)
                }
                communityAdapter.itemClickListener = object :PaperListAdapter.OnItemClickListener{
                    override fun OnItemClick(
                        holder: PaperListAdapter.MyViewHolder,
                        view: View,
                        data: VoteDetailDto,
                        time: String,
                        position: Int
                    ) {
                        // 투표 및 설문 상세로 화면 전환
                        val studentId = MyApplication.prefs.getString("studentId", "notExist").toInt()
                        val nickname = MyApplication.prefs.getString("nickname","notExist")
                        val status = data.status
                        if(data.voters != null){
                            if(data.voters.contains(studentId) || data.owner == nickname){
                                val intent = Intent(activity, VoteDetailActivity::class.java)
                                intent.putExtra("position",position)
                                intent.putExtra("time",time)
                                intent.putExtra("status",status)
                                startActivity(intent)
                            }
                            else Toast.makeText(context,"유권자가 아닙니다.", Toast.LENGTH_SHORT).show()
                        }else{
                            val intent = Intent(activity, VoteDetailActivity::class.java)
                            intent.putExtra("position",position)
                            intent.putExtra("time",time)
                            intent.putExtra("status",status)
                            startActivity(intent)
                        }
                    }
                }
                recyclerCommunityList.adapter = communityAdapter
            })

            //토글
            toggle.setOnClickListener {
                if(toggleFlag){
                    toggleBtn1.visibility = View.VISIBLE
                    toggleBtn2.visibility = View.INVISIBLE
                    toggle.setBackgroundResource(R.drawable.darkgray_color_box_50dp)
                    toggleFlag = false
                    setVoteList(toggleFlag)
                }else{
                    toggleBtn1.visibility = View.INVISIBLE
                    toggleBtn2.visibility = View.VISIBLE
                    toggle.setBackgroundResource(R.drawable.main_color_box_50dp)
                    toggleFlag = true
                    setVoteList(toggleFlag)
                }
            }
            val mActivity = activity as NavigationActivity

            //다이얼로그
            fab.setOnClickListener {
                val args = Bundle()
                args.putBoolean("isGroup", false)
                val writeDialog = WriteDialog()
                writeDialog.arguments = args
                writeDialog.show(mActivity.supportFragmentManager, "WriteDialog")
            }
        }
    }

    private fun setVoteList(toggleFlag : Boolean){
        //투표 리스트 가져오기
        voteModel!!.getAllVote(object: VoteCallback{
            val voteList = arrayListOf<VoteDetailDto>()
            override fun apiCallback(flag: Boolean, _list: List<VoteDetailDto>?) {
                if(flag && _list != null){
                    Log.e("COMMUNITY",_list.toString())
                    for(i in _list.indices){
                        if(_list[i].groupId == null) {
                            if(toggleFlag){
                                //참여함
                            }else{
                                //참여 안 함
                            }
                            voteList.add(_list[i])
                        }
                    }
                    voteModel.setMyVote(voteList)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
