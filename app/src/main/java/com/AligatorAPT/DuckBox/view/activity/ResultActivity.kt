package com.AligatorAPT.DuckBox.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.AligatorAPT.DuckBox.R
import com.AligatorAPT.DuckBox.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultBinding
    private var isType = 0

    private val _resultTitle = arrayOf(
        "그룹가입 요청 완료",
        "그룹탈퇴 완료",
        "그룹삭제 완료",
        "신고하기 완료",
    )

    private val _resultIcon = arrayOf(
        R.drawable.duck_main,
        R.drawable.duck_main,
        R.drawable.duck_main,
        R.drawable.duck_main,
    )

    private val _resultBigText = arrayOf(
        "가입 요청이\n전송되었습니다.",
        "그룹에서\n탈퇴되었습니다.",
        "그룹 삭제가\n완료되었습니다.",
        "신고 내역이\n접수되었습니다."
    )

    private var _resultSmallText = arrayOf(
        "",
        "탈퇴 시 작성된 게시글과 참여 내역은 자동으로 삭제되지 않습니다. 참고해주시기 바랍니다.",
        "그룹 삭제 시 작성된 게시글과 참여 내역은 자동으로 삭제되지 않습니다. 참고해주시기 바랍니다.",
        "이용에 불편을 드려 죄송합니다.\n신고 접수해주신 내용들은 운영 정책 위반 사실이 있는 지 꼼꼼하게 확인하겠습니다. 감사합니다."
    )

    private val _resultBtn = arrayOf(
        "나의 그룹 보러가기",
        "홈으로 돌아가기",
        "홈으로 돌아가기",
        "홈으로 돌아가기"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        isType = intent.getIntExtra("isType", 0)
        if(isType == 0){
            val groupName = intent.getStringExtra("groupName")!!
            _resultSmallText[0] = "$groupName 그룹에 요청이 전송되었습니다.\n" +
                    "2명의 그룹원 승인이 완료되면 가입이 확정됩니다."
        }

        binding.apply {
            resultBigText.text = _resultBigText[isType]
            resultBtn.text = _resultBtn[isType]
            resultIcon.setImageResource(_resultIcon[isType])
            resultSmallText.text = _resultSmallText[isType]
            resultTitle.text = _resultTitle[isType]

            //버튼 이벤트
            resultBackBtn.setOnClickListener {
                onBackPressed()
            }

            resultBtn.setOnClickListener {
                val intent = Intent(this@ResultActivity, NavigationActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) //홈으로 이동하므로 스택 비우기
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        when(isType){
            0 -> super.onBackPressed()
            1, 2, 3 -> {
                val intent = Intent(this@ResultActivity, NavigationActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
            else -> super.onBackPressed()
        }
    }
}
