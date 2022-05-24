package com.AligatorAPT.DuckBox.view.fragment.group

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.AligatorAPT.DuckBox.databinding.FragmentMutualAuthBinding
import com.AligatorAPT.DuckBox.dto.ethereum.Requester
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import com.AligatorAPT.DuckBox.view.activity.GroupActivity
import com.AligatorAPT.DuckBox.view.adapter.MutualAuthAdapter
import com.AligatorAPT.DuckBox.viewmodel.GroupViewModel
import com.AligatorAPT.DuckBox.viewmodel.SingletonGroupsContract
import kotlin.contracts.contract

class MutualAuthFragment : Fragment() {
    private var _binding: FragmentMutualAuthBinding? = null
    private val binding: FragmentMutualAuthBinding get() = _binding!!

    private val model: GroupViewModel by activityViewModels()
    private val contractModel = SingletonGroupsContract.getInstance()

    private lateinit var mutualAuthAdapter : MutualAuthAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMutualAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init(){
        val mActivity = activity as GroupActivity

        //데이터 가져오기
        model.id.observe(viewLifecycleOwner, Observer {
            contractModel?.getRequesterList(it)
        })

        //list 어뎁터 등록
        mutualAuthAdapter = MutualAuthAdapter(arrayListOf())
        mutualAuthAdapter.itemClickListener = object: MutualAuthAdapter.OnItemClickListener{
            override fun OnItemClick(
                holder: MutualAuthAdapter.MyViewHolder,
                view: View,
                data: Requester,
                position: Int
            ) {
                //승인하기 버튼 이벤트
                model.id.observe(viewLifecycleOwner, Observer {
                    contractModel?.approveMember(
                        groupId = it,
                        approverDid = MyApplication.prefs.getString("did","notExist"),
                        requesterDid = data.did
                    )
                })

                mutualAuthAdapter.deleteData(position)
            }
        }

        //데이터 등록
        contractModel?.requester?.observe(viewLifecycleOwner, Observer {
            val arrayList = ArrayList<Requester>()
            if (it != null) {
                arrayList.addAll(it.toTypedArray())
            }
            mutualAuthAdapter.setData(arrayList)
        })

        binding.apply {
            recyclerView.adapter = mutualAuthAdapter

            //그룹 정보
            //그룹 정보 추가
            model.name.observe(viewLifecycleOwner, Observer {
                groupTitle.text = it
            })
            model.description.observe(viewLifecycleOwner, Observer {
                groupDescription.text = it
            })


            //버튼 이벤트
            backBtn.setOnClickListener {
                mActivity.onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
