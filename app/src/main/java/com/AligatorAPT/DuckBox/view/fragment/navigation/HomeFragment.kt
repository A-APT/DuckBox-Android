package com.AligatorAPT.DuckBox.view.fragment.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentHomeBinding
import com.AligatorAPT.DuckBox.dto.group.GroupDetailDto
import com.AligatorAPT.DuckBox.dto.paper.VoteDetailDto
import com.AligatorAPT.DuckBox.retrofit.callback.MyGroupCallback
import com.AligatorAPT.DuckBox.retrofit.callback.VoteCallback
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import com.AligatorAPT.DuckBox.view.activity.*
import com.AligatorAPT.DuckBox.view.adapter.MyGroupAdapter
import com.AligatorAPT.DuckBox.view.adapter.PaperListAdapter
import com.AligatorAPT.DuckBox.view.dialog.ModalDialog
import com.AligatorAPT.DuckBox.viewmodel.SingletonGroup
import com.AligatorAPT.DuckBox.viewmodel.VoteViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private lateinit var myGroupAdapter: MyGroupAdapter
    private lateinit var paperListAdapter: PaperListAdapter

    private var isParticipation = true
    private var isVerification = true
    private var voteList = arrayListOf<VoteDetailDto>()

    private val voteModel = VoteViewModel.VoteSingletonGroup.getInstance()
    private val model = SingletonGroup.getInstance()

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
        //??? ?????? ????????? ????????????
        model!!.getGroupsOfUserGroup(object: MyGroupCallback{
            override fun apiCallback(flag: Boolean, _list: List<GroupDetailDto>?) {
                if(_list != null){
                    model.setMyGroup(_list)
                    if(_list.isNotEmpty()){
                        binding.apply {
                            recyclerMyGroup.visibility = View.VISIBLE
                            emptyGroup1.visibility = View.GONE
                            emptyGroup2.visibility = View.GONE
                        }
                    }else{
                        binding.apply {
                            recyclerMyGroup.visibility = View.GONE
                            emptyGroup1.visibility = View.VISIBLE
                            emptyGroup2.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })

        //?????? ????????? ????????????
        setPaperList(false)

        val mActivity = activity as NavigationActivity

        binding.apply {
            createGroupBtn.setOnClickListener {
                if(isVerification){
                    //?????? ????????? ???????????? ??????
                    val intent = Intent(activity, CreateGroupActivity::class.java)
                    startActivity(intent)
                }else{
                    //???????????????
                    val bundle = Bundle()
                    bundle.putString("message", "?????? ????????? ????????????\n" +
                            "????????? ??????????????????.")
                    val modalDialog = ModalDialog()
                    modalDialog.arguments = bundle
                    modalDialog.itemClickListener = object : ModalDialog.OnItemClickListener{
                        override fun OnPositiveClick() {
                            modalDialog.dismiss()
                            //???????????? ??????
                            mActivity.selectedBottomNavigationItem(R.id.tab_my)
                        }

                        override fun OnNegativeClick() {
                            modalDialog.dismiss()
                        }
                    }
                    modalDialog.show(mActivity.supportFragmentManager, "ModalDialog")
                }
            }

            //MyGroup list ???????????? ????????? ??????
            model.myGroup.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    Log.d("MYGROUP", it.toString())
                    val arrayList = ArrayList<GroupDetailDto>()
                    arrayList.addAll(it)
                    myGroupAdapter = MyGroupAdapter(arrayList)
                }else{
                    //???????????? ???????????? ????????? ?????? ???
                    val arrayList = ArrayList<GroupDetailDto>()
                    myGroupAdapter = MyGroupAdapter(arrayList)
                }

                myGroupAdapter.itemClickListener = object :MyGroupAdapter.OnItemClickListener{
                    override fun OnItemClick(
                        holder: MyGroupAdapter.MyViewHolder,
                        view: View,
                        data: GroupDetailDto,
                        position: Int
                    ) {
                        //?????? ????????? ?????? ??????
                        val intent = Intent(activity, GroupActivity::class.java)
                        intent.putExtra("groupData", position)
                        startActivity(intent)
                    }
                }
                recyclerMyGroup.adapter = myGroupAdapter
            })

            //??????????????? ??????????????????
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
                        time: String,
                        position: Int
                    ) {
                        // ?????? ??? ?????? ????????? ?????? ??????
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
                            else Toast.makeText(context,"???????????? ????????????.", Toast.LENGTH_SHORT).show()
                        }else{
                            val intent = Intent(activity, VoteDetailActivity::class.java)
                            intent.putExtra("position",position)
                            intent.putExtra("time",time)
                            intent.putExtra("status",status)
                            startActivity(intent)
                        }
                    }
                }
                recyclerPaperList.adapter = paperListAdapter
            })

            //?????? ?????? ?????? ?????? ??????
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
            //?????? ?????? ?????? ?????? ??????
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
            override fun apiCallback(flag: Boolean, _list: List<VoteDetailDto>?) {
                if(flag && _list != null){
                    Log.e("HOME",_list.toString())
                    for(i in _list.indices){
                        if(_list[i].groupId != null) {
                            if(toggleFlag){
                                //?????????
                            }else{
                                //?????? ??? ???
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
