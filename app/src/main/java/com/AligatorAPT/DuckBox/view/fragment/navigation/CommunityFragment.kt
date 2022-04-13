package com.AligatorAPT.DuckBox.view.fragment.navigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentCommunityBinding
import com.AligatorAPT.DuckBox.view.activity.*
import com.AligatorAPT.DuckBox.view.adapter.BannerAdapter
import com.AligatorAPT.DuckBox.view.adapter.PaperListAdapter
import com.AligatorAPT.DuckBox.view.data.PaperListData
import com.AligatorAPT.DuckBox.view.dialog.WriteDialog

class CommunityFragment : Fragment() {
    private var _binding: FragmentCommunityBinding? = null
    private val binding: FragmentCommunityBinding get() = _binding!!

    private lateinit var communityAdapter: PaperListAdapter
    private lateinit var bannerAdapter: BannerAdapter

    private var toggleFlag = false

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
            //배너
            bannerAdapter = BannerAdapter(setBanner())
            bannerAdapter.itemClickListener = object :BannerAdapter.OnItemClickListener{
                override fun OnItemClick(
                    holder: BannerAdapter.MyViewHolder,
                    view: View,
                    data: Int,
                    position: Int
                ) {
                    //배너 화면으로 전환
                }
            }
            viewpagerBanner.adapter = bannerAdapter
            viewpagerBanner.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            //배너 indicator
            indicator.setViewPager(viewpagerBanner)


            //리사이클러뷰
            communityAdapter = PaperListAdapter((setCommunicationList()))
            communityAdapter.itemClickListener = object :PaperListAdapter.OnItemClickListener{
                override fun OnItemClick(
                    holder: PaperListAdapter.MyViewHolder,
                    view: View,
                    data: PaperListData,
                    position: Int
                ) {
                    // 투표 및 설문 상세로 화면 전환
                    if(data.isVote){
                        val intent = Intent(activity, VoteDetailActivity::class.java)
                        startActivity(intent)
                    }else{
                        val intent = Intent(activity, PollDetailActivity::class.java)
                        startActivity(intent)
                    }
                }

            }
            recyclerCommunityList.adapter = communityAdapter

            //토글
            toggle.setOnClickListener {
                if(toggleFlag){
                    toggleBtn1.visibility = View.VISIBLE
                    toggleBtn2.visibility = View.INVISIBLE
                    toggle.setBackgroundResource(R.drawable.darkgray_color_box_50dp)
                    toggleFlag = false
                    communityAdapter.setData(setCommunicationList())
                }else{
                    toggleBtn1.visibility = View.INVISIBLE
                    toggleBtn2.visibility = View.VISIBLE
                    toggle.setBackgroundResource(R.drawable.main_color_box_50dp)
                    toggleFlag = true
                    communityAdapter.setData(setParticipationCommunicationList())
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

    private fun setBanner(): ArrayList<Int>{
        return arrayListOf<Int>(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3)
    }

    private fun setCommunicationList(): ArrayList<PaperListData>{
        return arrayListOf<PaperListData>(
            PaperListData(R.drawable.sub4_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", 100, 50),
            PaperListData(R.drawable.sub1_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", 100, 50),
            PaperListData(R.drawable.sub2_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", 100, 50),
            PaperListData(R.drawable.sub5_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, true, "3일 06:05:03 남음", 100, 50),
        )
    }
    private fun setParticipationCommunicationList(): ArrayList<PaperListData>{
        return arrayListOf<PaperListData>(
            PaperListData(R.drawable.sub5_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, false, "3일 06:05:03 남음", 100, 50),
            PaperListData(R.drawable.sub1_color_box_3dp, "건국대학교 제47회 공과대학 학생회 투표", "KU총학생회", true, false, "3일 06:05:03 남음", 100, 50),
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
