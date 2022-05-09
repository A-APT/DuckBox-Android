package com.AligatorAPT.DuckBox.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.AligatorAPT.DuckBox.databinding.ActivityLoginBinding
import com.AligatorAPT.DuckBox.dto.user.LoginRequestDto
import com.AligatorAPT.DuckBox.retrofit.callback.ApiCallback
import com.AligatorAPT.DuckBox.sharedpreferences.MyApplication
import com.AligatorAPT.DuckBox.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    private val model: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.apply{
            //로그인
            loginBtn.setOnClickListener {
                model.login(
                    LoginRequestDto(
                        email = loginEmail.text.toString(),
                        password = loginPwd.text.toString()
                    ), object : ApiCallback {
                        override fun apiCallback(flag: Boolean) {
                            if(flag){
                                Log.d("TOKEN", MyApplication.prefs.getString("token", "notExist"))
                                Log.d("REFRESHTOKEN", MyApplication.prefs.getString("refreshToken", "notExist"))
                                Log.d("STUDENTID", MyApplication.prefs.getString("studentId","notExist"))
                                Log.d("DID", MyApplication.prefs.getString("did","notExist"))
                                //이메일 저장
                                MyApplication.prefs.setString("email",loginEmail.text.toString())

                                val intent = Intent(this@LoginActivity, NavigationActivity::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(this@LoginActivity, "회원 정보가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
            }

            //회원가입
            registerBtn.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
}