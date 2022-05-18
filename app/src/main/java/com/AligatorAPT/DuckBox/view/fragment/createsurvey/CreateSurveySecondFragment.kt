package com.AligatorAPT.DuckBox.view.fragment.createsurvey

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.FragmentCreateSurveySecondBinding
import com.AligatorAPT.DuckBox.dto.paper.QuestionType
import com.AligatorAPT.DuckBox.view.activity.CreateSurveyActivity
import com.AligatorAPT.DuckBox.view.adapter.createsurvey.SurveySecondRVAdapter
import com.AligatorAPT.DuckBox.viewmodel.CreateVoteViewModel

class CreateSurveySecondFragment: Fragment()  {
    private var _binding : FragmentCreateSurveySecondBinding? = null
    private val binding : FragmentCreateSurveySecondBinding get() = _binding!!
    val viewModel : CreateVoteViewModel by activityViewModels()
    var checkValidation = booleanArrayOf(false)
    private lateinit var surveySecondRVAdapter: SurveySecondRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateSurveySecondBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        check()
    }


    private fun initList() {

        surveySecondRVAdapter = SurveySecondRVAdapter(viewModel, childFragmentManager, requireContext())
        binding.apply {
            val layoutManager = LinearLayoutManager(requireContext())
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            surveySecondListRv.layoutManager = layoutManager
            surveySecondListRv.adapter = surveySecondRVAdapter

            surveySecondAddTv.setOnClickListener {
                surveySecondRVAdapter.addData()
                surveyNum.text = ("가능한 질문 개수 ${surveySecondRVAdapter.itemCount} / 10")
            }
        }

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                from: RecyclerView.ViewHolder,
                to: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                surveySecondRVAdapter.removeData(viewHolder.layoutPosition)
                binding.surveyNum.text = ("가능한 질문 개수 ${surveySecondRVAdapter.itemCount} / 10")
                binding.surveySecondListRv[viewHolder.layoutPosition].clearFocus()
            }
        }
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.surveySecondListRv)
    }

    private fun check() {
        binding.apply {
            viewModel.questionData.observe(viewLifecycleOwner,{
                Log.e("OBSERVER",it!!.size.toString()+"내용:"+it.toString())
                var isValid = false
                for(i in 0 until it.size){
                    if(it.size>=2 && it[i].question != "" && it[i].type != null){
                        if(it[i].type == QuestionType.MULTI){
                            if(!it[i].candidates!!.contains("")){
                                isValid = true
                                continue
                            }
                            else{
                                isValid = false
                                break
                            }
                        }else {
                            isValid = true
                            continue
                        }
                    }else {
                        isValid = false
                        break
                    }
                }
                checkValidation[0] = isValid
                if(it.size == 10) {surveySecondAddTv.visibility = View.GONE}
                else {surveySecondAddTv.visibility = View.VISIBLE}
                setIsActivateBtn()
            })
        }
    }

    fun setIsActivateBtn(){
        val mActivity = activity as CreateSurveyActivity
        binding.apply {
            if(checkValidation[0]){
                mActivity.binding.createPollNextTv.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.main))
                mActivity.binding.createPollNextTv.isEnabled = true
                mActivity.checkValidation[1] = true
            }else{
                mActivity.binding.createPollNextTv.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.darkgray))
                mActivity.binding.createPollNextTv.isEnabled = false
                mActivity.checkValidation[1] = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}