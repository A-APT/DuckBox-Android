package com.AligatorAPT.DuckBox.view.adapter.createsurvey

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.RowSurveyBinding
import com.AligatorAPT.DuckBox.dto.vote.Question
import com.AligatorAPT.DuckBox.dto.vote.QuestionType
import com.AligatorAPT.DuckBox.view.dialog.surveyTypeDialog
import com.AligatorAPT.DuckBox.viewmodel.CreateVoteViewModel

class SurveySecondRVAdapter(val model: CreateVoteViewModel, val childFragmentManager: FragmentManager, val context: Context)
    : RecyclerView.Adapter<SurveySecondRVAdapter.ViewHolder>(){

    var surveyList: ArrayList<Question> = arrayListOf(Question(null,"", arrayListOf("")))

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = RowSurveyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SurveySecondRVAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.apply { 
            surveyQuestion.setText(surveyList[position].question)
            if(surveyList[position].type == QuestionType.MULTI){
                surveyType.text = ""
                surveySelectedIv.visibility = View.VISIBLE
                surveySelectedIv.setImageResource(R.drawable.filled_circle)
                surveySelectedTv.visibility = View.VISIBLE
                surveySelectedTv.text = "객관식"
                surveyRv.visibility = View.VISIBLE
                surveyAddoption.visibility = View.VISIBLE
            }else if(surveyList[position].type == QuestionType.LIKERT){
                surveyType.text = ""
                surveySelectedIv.visibility = View.VISIBLE
                surveySelectedIv.setImageResource(R.drawable.linear_black)
                surveySelectedTv.visibility = View.VISIBLE
                surveySelectedTv.text = "선형배율"
                surveyRv.visibility = View.GONE
                surveyAddoption.visibility = View.GONE
            }else{
                surveyType.text = "유형 선택"
                surveyRv.visibility = View.GONE
                surveyAddoption.visibility = View.GONE
                surveySelectedIv.visibility = View.GONE
                surveySelectedTv.visibility = View.GONE
            }
            Log.e("SURVEY_QUESTION","position:$position ${model.questionData.value} / surveyList: $surveyList")
            val surveyMultipleRVAdapter = SurveyMultipleRVAdapter(surveyList[position].candidates as ArrayList<String>)
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            surveyRv.layoutManager = layoutManager
            surveyRv.adapter = surveyMultipleRVAdapter

            surveyAddoption.setOnClickListener {
                surveyMultipleRVAdapter.addData()
            }

            surveyMultipleRVAdapter.listener = object:SurveyMultipleRVAdapter.multipleListener{
                override fun onMultipleChange(list: ArrayList<String>) {
                    surveyList[position].candidates = list
                    Log.e("SURVEY_CANDIDATE","position:$position ${model.questionData.value}")
                    model.setQuestionsData(surveyList)
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
                    surveyMultipleRVAdapter.removeData(viewHolder.layoutPosition)
                    surveyRv[viewHolder.layoutPosition].clearFocus()
                }
            }
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(surveyRv)
        }
    }

    override fun getItemCount(): Int = surveyList.size

    inner class ViewHolder(val binding: RowSurveyBinding): RecyclerView.ViewHolder(binding.root){
        init{
            binding.apply {
                surveyType.setOnClickListener {
                    val dialog = surveyTypeDialog()
                    dialog.show(childFragmentManager, "surveyDialog")
                    dialog.surveyListner = object: surveyTypeDialog.surveyDialogListener{
                        override fun onDialogMultipleClick() {
                            surveyList[adapterPosition].type = QuestionType.MULTI
                            surveyType.text = ""
                            surveySelectedIv.visibility = View.VISIBLE
                            surveySelectedIv.setImageResource(R.drawable.filled_circle)
                            surveySelectedTv.visibility = View.VISIBLE
                            surveySelectedTv.text = "객관식"
                            Log.e("SURVEY_TYPE","position:$adapterPosition ${model.questionData.value}")
                            model.setQuestionsData(surveyList)
                            notifyDataSetChanged()
                            surveyRv.visibility = View.VISIBLE
                            surveyAddoption.visibility = View.VISIBLE
                        }

                        override fun onDialogLinearClick() {
                            surveyList[adapterPosition].type = QuestionType.LIKERT
                            surveyList[adapterPosition].candidates = arrayListOf("")
                            surveyType.text = ""
                            surveySelectedIv.visibility = View.VISIBLE
                            surveySelectedIv.setImageResource(R.drawable.linear_black)
                            surveySelectedTv.visibility = View.VISIBLE
                            surveySelectedTv.text = "선형배율"
                            Log.e("SURVEY_TYPE","position:$adapterPosition ${model.questionData.value}")
                            model.setQuestionsData(surveyList)
                            notifyDataSetChanged()
                            surveyRv.visibility = View.GONE
                            surveyAddoption.visibility = View.GONE
                        }
                    }
                }

                surveyQuestion.addTextChangedListener(object: TextWatcher{
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        surveyList[adapterPosition].question = p0.toString()
                        Log.e("SURVEY_QUESTION","position:$adapterPosition ${model.questionData.value}")
                        model.setQuestionsData(surveyList)
                    }

                    override fun afterTextChanged(p0: Editable?) {

                    }

                })
            }
        }
    }

    fun addData(){
        surveyList.add(Question(null,"", arrayListOf("")))
        notifyItemInserted(surveyList.size)
        Log.e("surveyList",surveyList.toString())
    }

    fun removeData(pos: Int){
        surveyList.removeAt(pos)
        notifyDataSetChanged()
        Log.e("surveyList",surveyList.toString())
    }
}