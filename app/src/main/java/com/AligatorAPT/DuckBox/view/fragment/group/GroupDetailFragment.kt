package com.AligatorAPT.DuckBox.view.fragment.group

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentGroupDetailBinding
import com.AligatorAPT.DuckBox.retrofit.callback.ApiCallback
import com.AligatorAPT.DuckBox.retrofit.callback.VoteCallback
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import com.AligatorAPT.DuckBox.view.activity.*
import com.AligatorAPT.DuckBox.view.adapter.PaperListAdapter
import com.AligatorAPT.DuckBox.view.data.PaperListData
import com.AligatorAPT.DuckBox.view.data.VoteDetailDto
import com.AligatorAPT.DuckBox.view.dialog.ModalDialog
import com.AligatorAPT.DuckBox.view.dialog.WriteDialog
import com.AligatorAPT.DuckBox.viewmodel.GroupViewModel
import com.AligatorAPT.DuckBox.viewmodel.VoteViewModel
import com.google.android.material.tabs.TabLayout

class GroupDetailFragment : Fragment() {
    private var _binding: FragmentGroupDetailBinding? = null
    private val binding: FragmentGroupDetailBinding get() = _binding!!

    private val model: GroupViewModel by activityViewModels()
    private val voteModel = VoteViewModel.VoteSingletonGroup.getInstance()

    private lateinit var paperListAdapter: PaperListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init(){
        val mActivity = activity as GroupActivity
        //그룹 권한 초기화
        model.setAuthority(GroupViewModel.Authority.MASTER)

        binding.apply {
            //그룹 정보 추가
            model.name.observe(viewLifecycleOwner, Observer {
                groupTitle.text = it
            })
            model.description.observe(viewLifecycleOwner, Observer {
                groupDescription.text = it
            })

            //이미지
            model.header.observe(viewLifecycleOwner, Observer {
                Log.e("HEADER::", it.toString())
                if(it == null){
                    groupBackgroundImg.setImageResource(R.drawable.sub1_color_box_5dp)
                }else{
                    val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                    groupBackgroundImg.setImageBitmap(bitmap)
                }
            })

            //그룹 가입 여부
            model.authority.observe(viewLifecycleOwner, Observer {
                if (it == GroupViewModel.Authority.MEMBER || it == GroupViewModel.Authority.MASTER) {
                    joinGroup.visibility = View.GONE
                    mutualAuthentication.visibility = View.VISIBLE
                }else{
                    joinGroup.visibility = View.VISIBLE
                    mutualAuthentication.visibility = View.GONE
                }
            })

            //버튼 이벤트
            mutualAuthentication.setOnClickListener {
                model.authority.observe(viewLifecycleOwner, Observer {
                    if (it == GroupViewModel.Authority.MEMBER || it == GroupViewModel.Authority.MASTER) {
                        mActivity.changeFragment(MutualAuthFragment())
                    }
                })
            }

            joinGroup.setOnClickListener {
                model.authority.observe(viewLifecycleOwner, Observer {
                    if (it == GroupViewModel.Authority.OTHER) {
                        //다이얼로그
                        val bundle = Bundle()
                        bundle.putString("message", "그룹에 가입하시겠습니까?")
                        val modalDialog = ModalDialog()
                        modalDialog.arguments = bundle
                        modalDialog.itemClickListener = object : ModalDialog.OnItemClickListener{
                            override fun OnPositiveClick() {
                                modalDialog.dismiss()
                                model.joinGroup(object: ApiCallback {
                                    override fun apiCallback(flag: Boolean) {
                                        if(flag){
                                            //그룹 가입 요청 완료로 화면 전환
                                            val intent = Intent(mActivity, ResultActivity::class.java)
                                            intent.putExtra("isType", 0)
                                            model.name.observe(viewLifecycleOwner, Observer {
                                                intent.putExtra("groupName", it)
                                            })
                                            startActivity(intent)
                                        }
                                    }
                                })
                            }

                            override fun OnNegativeClick() {
                                modalDialog.dismiss()
                            }
                        }
                        modalDialog.show(mActivity.supportFragmentManager, "ModalDialog")
                    }
                })
            }

            backBtn.setOnClickListener {
                mActivity.onBackPressed()
            }

            groupSetting.setOnClickListener {
                //그룹 설정로 화면 전환
                mActivity.changeFragment(GroupSettingFragment())
            }


            //그룹 투표 리스트 가져오기
            setPaperList(0)

            //그룹 투표리스트 리사이클러뷰
            voteModel!!.myVote.observe(viewLifecycleOwner, Observer {
                if(it != null){
                    Log.e("GROUP_MYVOTE",it.toString())
                    val arr = ArrayList<VoteDetailDto>()
                    arr.addAll(it)
                    paperListAdapter = PaperListAdapter(arr)

                }else{
                    val arr = ArrayList<VoteDetailDto>()
                    paperListAdapter = PaperListAdapter(arr)
                }
                paperListAdapter.itemClickListener = object : PaperListAdapter.OnItemClickListener{
                    override fun OnItemClick(
                        holder: PaperListAdapter.MyViewHolder,
                        view: View,
                        data: VoteDetailDto,
                        time: String,
                        position: Int
                    ) {
                        model.authority.observe(viewLifecycleOwner, Observer {
                            if (it == GroupViewModel.Authority.OTHER) {
                                //다이얼로그
                                val bundle = Bundle()
                                bundle.putString("message", "그룹원만 열람할 수 있습니다.\n" +
                                        "그룹에 가입해주세요.")
                                val modalDialog = ModalDialog()
                                modalDialog.arguments = bundle
                                modalDialog.itemClickListener = object : ModalDialog.OnItemClickListener{
                                    override fun OnPositiveClick() {
                                        modalDialog.dismiss()
                                    }

                                    override fun OnNegativeClick() {
                                        modalDialog.dismiss()
                                    }
                                }
                                modalDialog.show(mActivity.supportFragmentManager, "ModalDialog")
                            }else{
                                // 투표 및 설문 상세로 화면 전환
                                val studentId = MyApplication.prefs.getString("studentId", "notExist").toInt()
                                Log.d("studentId", studentId.toString()+"candidate: "+data.candidates.toString())
                                if(data.voters != null){
                                    if(data.voters.contains(studentId)){
                                        val intent = Intent(activity, VoteDetailActivity::class.java)
                                        intent.putExtra("vote",data)
                                        intent.putExtra("time",time)
                                        startActivity(intent)
                                    }
                                    else Toast.makeText(context,"유권자가 아닙니다.", Toast.LENGTH_SHORT).show()
                                }else{
                                    val intent = Intent(activity, VoteDetailActivity::class.java)
                                    intent.putExtra("vote",data)
                                    intent.putExtra("time",time)
                                    startActivity(intent)
                                }
                            }
                        })
                    }
                }
                recyclerView.adapter = paperListAdapter
            })

            groupTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    setPaperList(tab!!.position)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })

            //다이얼로그
            fab.setOnClickListener {
                val args = Bundle()
                args.putBoolean("isGroup", true)
                args.putString("groupId",model.id.value)
                val writeDialog = WriteDialog()
                writeDialog.arguments = args
                writeDialog.show(mActivity.supportFragmentManager, "WriteDialog")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setPaperList(flag: Int) {
        var groupList = arrayListOf<VoteDetailDto>()
        when(flag){
            0->{//투표
                voteModel!!.getAllVote(object: VoteCallback {
                    override fun apiCallback(flag: Boolean, _list: ArrayList<VoteDetailDto>?) {
                        if(flag && _list != null){
                            for(i in 0 until _list.size){
                                if(_list[i].isGroup) {
                                    groupList.add(_list[i])
                                }
                            }
                            Log.e("GROUPDETAIL",groupList.toString())
                            voteModel.setMyVote(groupList)
                        }
                    }
                })
            }
            1->{//설문
                voteModel!!.getAllVote(object: VoteCallback{
                    override fun apiCallback(flag: Boolean, _list: ArrayList<VoteDetailDto>?) {
                        if(flag && _list != null){
                            for(i in 0 until _list.size){
                                if(_list[i].isGroup) {
                                    groupList.add(_list[i])
                                }
                            }
                            voteModel.setMyVote(groupList)
                        }
                    }
                })
            }
            2->{//참여 완료
                voteModel!!.getAllVote(object: VoteCallback{
                    override fun apiCallback(flag: Boolean, _list: ArrayList<VoteDetailDto>?) {
                        if(flag && _list != null){
                            for(i in 0 until _list.size){
                                if(_list[i].isGroup) {
                                    groupList.add(_list[i])
                                }
                            }
                            voteModel.setMyVote(groupList)
                        }
                    }
                })
            }
        }
    }
}
