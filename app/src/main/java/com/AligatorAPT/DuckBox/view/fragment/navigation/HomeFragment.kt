package com.AligatorAPT.DuckBox.view.fragment.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentHomeBinding
import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto
import com.AligatorAPT.DuckBox.retrofit.callback.MyGroupCallback
import com.AligatorAPT.DuckBox.retrofit.callback.VoteCallback
import com.AligatorAPT.DuckBox.view.activity.*
import com.AligatorAPT.DuckBox.view.adapter.MyGroupAdapter
import com.AligatorAPT.DuckBox.view.adapter.PaperListAdapter
import com.AligatorAPT.DuckBox.view.data.VoteDetailDto
import com.AligatorAPT.DuckBox.view.dialog.ModalDialog
import com.AligatorAPT.DuckBox.viewmodel.VoteViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private lateinit var myGroupAdapter: MyGroupAdapter
    private lateinit var paperListAdapter: PaperListAdapter

    private var isParticipation = true
    private var isVerification = true
    private var voteList = arrayListOf<VoteDetailDto>()

//    private val model = SingletonGroup.getInstance()
    private val voteModel = VoteViewModel.VoteSingletonGroup.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){

        //투표 리스트 가져오기
        setPaperList(false)

        val mActivity = activity as NavigationActivity

        binding.apply {
            createGroupBtn.setOnClickListener {
                if(isVerification){
                    //그룹 만들기 액티비티 이동
                    val intent = Intent(activity, CreateGroupActivity::class.java)
                    startActivity(intent)
                }else{
                    //다이얼로그
                    val bundle = Bundle()
                    bundle.putString("message", "모든 인증을 완료하고\n" +
                            "그룹을 만들어주세요.")
                    val modalDialog = ModalDialog()
                    modalDialog.arguments = bundle
                    modalDialog.itemClickListener = object : ModalDialog.OnItemClickListener{
                        override fun OnPositiveClick() {
                            modalDialog.dismiss()
                            //내정보로 이동
                            mActivity.selectedBottomNavigationItem(R.id.tab_my)
                        }

                        override fun OnNegativeClick() {
                            modalDialog.dismiss()
                        }
                    }
                    modalDialog.show(mActivity.supportFragmentManager, "ModalDialog")
                }
            }

            //투표리스트 리사이클러뷰
            voteModel!!.myVote.observe(viewLifecycleOwner, androidx.lifecycle.Observer{
                if(it != null){
                    Log.e("HOME_MYVOTE",it.toString())
                    val arr = ArrayList<VoteDetailDto>()
                    arr.addAll(it)
                    paperListAdapter = PaperListAdapter(arr)

                }else{
                    val arr = ArrayList<VoteDetailDto>()
                    paperListAdapter = PaperListAdapter(arr)
                }
                paperListAdapter.itemClickListener = object :PaperListAdapter.OnItemClickListener{
                    override fun OnItemClick(
                        holder: PaperListAdapter.MyViewHolder,
                        view: View,
                        data: VoteDetailDto,
                        position: Int
                    ) {
                        // 투표 및 설문 상세로 화면 전환
                        val intent = Intent(activity, VoteDetailActivity::class.java)
                        startActivity(intent)
                    }
                }
                recyclerPaperList.adapter = paperListAdapter
            })

            //참여 가능 버튼 누를 경우
            toggleParticipationPossible.setOnClickListener {
                if(!isParticipation){
                    toggleParticipationCompleted.setTextColor(ContextCompat.getColor(mActivity, R.color.darkgray))
                    toggleParticipationCompleted.setBackgroundResource(0)
                    toggleParticipationPossible.setTextColor(ContextCompat.getColor(mActivity, R.color.black))
                    toggleParticipationPossible.setBackgroundResource(R.drawable.white_color_box_50dp)
                    isParticipation = !isParticipation
                    setPaperList(isParticipation)
                }
            }
            //참여 완료 버튼 누를 경우
            toggleParticipationCompleted.setOnClickListener {
                if(isParticipation){
                    toggleParticipationPossible.setTextColor(ContextCompat.getColor(mActivity, R.color.darkgray))
                    toggleParticipationPossible.setBackgroundResource(0)
                    toggleParticipationCompleted.setTextColor(ContextCompat.getColor(mActivity, R.color.black))
                    toggleParticipationCompleted.setBackgroundResource(R.drawable.white_color_box_50dp)
                    isParticipation = !isParticipation
                    setPaperList(isParticipation)
                }
            }
        }

    }

    private fun setPaperList(toggleFlag : Boolean){
        voteModel!!.getAllVote(object: VoteCallback{
            override fun apiCallback(flag: Boolean, _list: ArrayList<VoteDetailDto>?) {
                if(flag && _list != null){
                    Log.e("HOME",_list.toString())
                    for(i in 0 until _list.size){
                        if(_list[i].isGroup){
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
