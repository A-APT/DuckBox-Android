package com.AligatorAPT.DuckBox

import BlindSecp256k1
import BlindedData
import Point
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.AligatorAPT.DuckBox.databinding.ActivityMainBinding
import com.AligatorAPT.DuckBox.ethereum.*
import com.AligatorAPT.DuckBox.view.activity.CreateVoteActivity
import com.AligatorAPT.DuckBox.view.activity.LoginActivity
import com.AligatorAPT.DuckBox.view.activity.NavigationActivity
import com.AligatorAPT.DuckBox.view.activity.SignUpActivity
import com.AligatorAPT.DuckBox.viewmodel.GroupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.AligatorAPT.DuckBox.viewmodel.SingletonGroupsContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.web3j.crypto.Credentials
import java.math.BigInteger
import java.security.Provider
import java.security.Security

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO
    private val contractModel = SingletonGroupsContract.getInstance()

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

            createGroup.setOnClickListener {
                contractModel?.registerGroup("duck", "67b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a")
            }

            approveGroup1.setOnClickListener {
                GroupsContract.approveGroupAuthentication("duck", "77b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a")
            }

            approveGroup2.setOnClickListener {
                GroupsContract.approveGroupAuthentication("duck", "87b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a")
            }

            requestJoinGroup.setOnClickListener {
                GroupsContract.requestMember("duck", "97b46dc256ee2d88130098d6309eb40023a02210f919cd03105801d7d50b655a", "jiwoo", "email@k.com")
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
//                val list = GroupsContract.getRequesterList("a")
//                Log.e("List::", list.toString())
            }

            VOTECONTRACT.setOnClickListener {
                CoroutineScope(dispatcher).launch{
                    val blindSecp256k1 = BlindSecp256k1()
                    val message = "0".encodeToByteArray()
                    val R_ = Point(
                        BigInteger("d80387d2861da050c1a8ae11c9a1ef5ed93572bd6537d50984c1dea2f2db912b", 16),
                        BigInteger("edcef3840df9cd47256996c460f0ce045ccb4fac5e914f619c44ad642779011", 16)
                    )
                    val blindedData: BlindedData = blindSecp256k1.blind(R_,message)

                    val r = arrayListOf(blindedData.R.x,blindedData.R.y)
                    val serverBsig = BigInteger("d80387dd93572bd6537d50984c1dea2f2db912b", 16)
                    val ownerBsig = BigInteger("edcef3840e914f619c44ad642779011", 16)
                    val serverSig = blindSecp256k1.unblind(blindedData.a, blindedData.b, serverBsig)
                    val voteOwnerSig = blindSecp256k1.unblind(blindedData.a, blindedData.b, ownerBsig)
                    // create new account
                    val pseudoCredentials: Credentials = EthereumManagement.createNewCredentials("PASSWORD") // TODO user password

                    BallotContract.vote(
                        _ballotId = "ballot id",
                        _m = "0",
                        _serverSig = serverSig,
                        _ownerSig = voteOwnerSig,
                        R = r,
                        pseudoCredentials = pseudoCredentials
                    )
                }
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
