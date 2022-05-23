package com.AligatorAPT.DuckBox.ethereum

import android.util.Log
import com.AligatorAPT.DuckBox.BuildConfig
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.*
import org.web3j.abi.datatypes.generated.Bytes32
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import java.math.BigInteger
import javax.xml.bind.DatatypeConverter
import kotlin.streams.toList

object BallotContract {

    private val ethereumManagement: EthereumManagement = EthereumManagement
    private const val contractAddress: String = BuildConfig.BALLOT_ADDRESS

    private final val REGISTER = "registerBallot"
    private final val OPEN = "open"
    private final val CLOSE = "close"
    private final val RESULT = "resultOfBallot"
    private final val VOTE = "vote"

    fun registerBallot(did: String,
                       ballotId: String,
                       publicKeyX: BigInteger,
                       publicKeyY: BigInteger,
                       candidateNames: List<String>,
                       isOfficial: Boolean,
                       startTime: Long, // milliseconds
                       endTime: Long // milliseconds
    ): Boolean? {
        Log.d("REGISTER_VOTE_ADDRESS", contractAddress)
        ethereumManagement.setCredentials(BuildConfig.BALLOT_PK)
        val candidateList: List<Utf8String> = candidateNames.stream().map {
            Utf8String(it)
        }.toList()
        val candidateDynamicArray = DynamicArray(Utf8String::class.java, candidateList)
        val inputParams = listOf<Type<*>>(
            Bytes32(DatatypeConverter.parseHexBinary(did)),
            Uint256(publicKeyX),
            Uint256(publicKeyY),
            Utf8String(ballotId),
            candidateDynamicArray,
            Bool(isOfficial),
            Uint256(startTime),
            Uint256(endTime)
        )
        val outputParams = listOf<TypeReference<*>>(object: TypeReference<Bool>() {})
        return ethereumManagement.ethSendRaw(contractAddress, REGISTER, inputParams, outputParams) as Boolean?
    }

    fun open(ballotId: String) {
        val inputParams = listOf<Type<*>>(Utf8String(ballotId))
        val outputParams = listOf<TypeReference<*>>()
        ethereumManagement.ethSendRaw(contractAddress, OPEN, inputParams, outputParams)
    }

    fun close(ballotId: String, totalNum: Int) {
        val inputParams = listOf<Type<*>>(Utf8String(ballotId), Uint256(totalNum.toLong()))
        val outputParams = listOf<TypeReference<*>>()
        ethereumManagement.ethCall(contractAddress, CLOSE, inputParams, outputParams)
    }

    fun resultOfBallot(ballotId: String): List<BigInteger> {
        Log.d("RESULT_ADDRESS", contractAddress)
        val inputParams = listOf<Type<*>>(Utf8String(ballotId))
        val outputParams = listOf<TypeReference<*>>(object: TypeReference<DynamicArray<Uint>>() {})
        val decoded: List<Type<*>> = EthereumManagement.ethCall(contractAddress, RESULT, inputParams, outputParams)!!
        val result: MutableList<BigInteger> = mutableListOf()
        (decoded[0].value as List<Uint>).forEach {
            result.add(it.value)
        }
        return result
    }

    fun vote(
        _ballotId: String,
        _m: String,
        _serverSig: BigInteger,
        _ownerSig: BigInteger,
        R: ArrayList<BigInteger>,
        pseudoCredentials: Credentials,
    ){
        Log.d("VOTE_ADDRESS", contractAddress)
        ethereumManagement.setCredentials(BuildConfig.BALLOT_PK)
        val RList: List<Uint256> = R.stream().map {
            Uint256(it)
        }.toList()

        val rListDynamicArray = DynamicArray(Uint256::class.java, RList)
        val inputParams = listOf<Type<*>>(
            Utf8String(_ballotId),
            Bytes32(DatatypeConverter.parseHexBinary(_m)),
            Uint256(_serverSig),
            Uint256(_ownerSig),
            rListDynamicArray)
        val outputParams = listOf<TypeReference<*>>()
        ethereumManagement.ethSendRaw(contractAddress, VOTE, inputParams, outputParams, pseudoCredentials)
    }
}