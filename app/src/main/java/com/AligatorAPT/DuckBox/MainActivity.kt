package com.AligatorAPT.DuckBox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.AligatorAPT.DuckBox.databinding.ActivityMainBinding
import com.AligatorAPT.DuckBox.ethereum.DIDContract
import com.AligatorAPT.DuckBox.ethereum.GanacheAddress
import com.AligatorAPT.DuckBox.ethereum.GroupsContract
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity
import com.AligatorAPT.DuckBox.view.activity.LoginActivity
import com.AligatorAPT.DuckBox.view.activity.NavigationActivity
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Provider
import java.security.Security

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

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
                DIDContract.registerDid(GanacheAddress.GROUP_OWNER,"groupOwner")
                DIDContract.registerDid(GanacheAddress.APPROVER1,"approver1")
                DIDContract.registerDid(GanacheAddress.APPROVER2,"approver2")
                DIDContract.registerDid(GanacheAddress.USER1,"user1")
                DIDContract.registerDid(GanacheAddress.USER2,"user2")
            }

            createGroup.setOnClickListener {
//                GroupsContract.testFunction("adsfasdf")
                GroupsContract.testFunction("aaaa", "groupOwner")
            }

            approveGroup1.setOnClickListener {
                GroupsContract.approveGroupAuthentication1("aaaa", "approver1")
            }

            approveGroup2.setOnClickListener {
                GroupsContract.approveGroupAuthentication2("aaaa", "approver2")
            }

            requestJoinGroup.setOnClickListener {
                GroupsContract.requestMember("aaaa", "user1")
            }

            approveJoin1.setOnClickListener {
                GroupsContract.approveMember1("aaaa", "approver1", "user1")
            }

            approveJoin2.setOnClickListener {
                GroupsContract.approveMember2("aaaa", "approver1", "user1")
            }

            exitMember.setOnClickListener {
                GroupsContract.exitMember("aaaa","user1")
            }

            deleteGroup.setOnClickListener {
                GroupsContract.deleteGroup("aaaa","groupOwner")
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
