package com.AligatorAPT.DuckBox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.AligatorAPT.DuckBox.databinding.ActivityMainBinding
import com.AligatorAPT.DuckBox.ethereum.DIDContract
import com.AligatorAPT.DuckBox.ethereum.GanacheAddress
import com.AligatorAPT.DuckBox.view.activity.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Provider
import java.security.Security

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBouncyCastle()

        init()
    }

    private fun init() {
        binding.apply {
            //회원가입 화면 전환
            signUpButton.setOnClickListener {
                val intent = Intent(this@MainActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
            //네비게이션 화면 전환
            navigationButton.setOnClickListener {
                val intent = Intent(this@MainActivity, NavigationActivity::class.java)
                startActivity(intent)
            }

            createVoteButton.setOnClickListener {
                val intent = Intent(this@MainActivity, CreateVoteActivity::class.java)
                startActivity(intent)
            }

            LoginButton.setOnClickListener {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }

            DID.setOnClickListener {
                CoroutineScope(dispatcher).launch{
                    DIDContract.registerDid(GanacheAddress.USER1,"user1")
                }
            }

            SURVEY.setOnClickListener {
                val intent = Intent(this@MainActivity, CreateSurveyActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setupBouncyCastle() {
        val provider: Provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)
            ?: // Web3j will set up the provider lazily when it's first used.
            return
        if (provider.javaClass == BouncyCastleProvider::class.java) {
            // BC with same package name, shouldn't happen in real life.
            return
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.insertProviderAt(BouncyCastleProvider(), 1)
    }

}
