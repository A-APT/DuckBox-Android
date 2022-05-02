package com.AligatorAPT.DuckBox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.AligatorAPT.DuckBox.databinding.ActivityMainBinding
import com.AligatorAPT.DuckBox.ethereum.DIDContract
import com.AligatorAPT.DuckBox.ethereum.GanacheAddress
import com.AligatorAPT.DuckBox.ethereum.GroupsContract
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity
import com.AligatorAPT.DuckBox.view.activity.LoginActivity
import com.AligatorAPT.DuckBox.view.activity.NavigationActivity
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity
import com.AligatorAPT.DuckBox.viewmodel.TestViewModel
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Provider
import java.security.Security

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val model: TestViewModel by viewModels()

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
                DIDContract.registerDid(GanacheAddress.GROUP_OWNER,"67b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a")
                DIDContract.registerDid(GanacheAddress.APPROVER1,"77b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a")
                DIDContract.registerDid(GanacheAddress.APPROVER2,"87b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a")
                DIDContract.registerDid(GanacheAddress.USER1,"97b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a")
                DIDContract.registerDid(GanacheAddress.USER2,"a7b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a")
            }

            createGroup.setOnClickListener {
                model.registerGroup("duck", "67b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a")
            }

            approveGroup1.setOnClickListener {
                GroupsContract.approveGroupAuthentication("duck", "77b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a")
            }

            approveGroup2.setOnClickListener {
                GroupsContract.approveGroupAuthentication("duck", "87b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a")
            }

            requestJoinGroup.setOnClickListener {
                GroupsContract.requestMember("duck", "97b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a")
            }

            approveJoin1.setOnClickListener {
                GroupsContract.approveMember("duck", "77b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a", "97b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a")
            }

            approveJoin2.setOnClickListener {
                GroupsContract.approveMember("duck", "87b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a", "97b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a")
            }

            exitMember.setOnClickListener {
                GroupsContract.exitMember("duck","97b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a")
            }

            deleteGroup.setOnClickListener {
                GroupsContract.deleteGroup("duck","67b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a")
            }

            getList.setOnClickListener {
                val list = GroupsContract.getRequesterList("duck")
                Log.e("List::", list.toString())
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
