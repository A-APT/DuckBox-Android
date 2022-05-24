package com.AligatorAPT.DuckBox.ethereum

import android.util.Log
import com.AligatorAPT.DuckBox.BuildConfig
import com.AligatorAPT.DuckBox.dto.ethereum.BallotData
import com.AligatorAPT.DuckBox.dto.ethereum.VoteData
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.*
import org.web3j.abi.datatypes.generated.Bytes32
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger
import javax.xml.bind.DatatypeConverter
import kotlin.streams.toList

object BallotContract {

    private val ethereumManagement: EthereumManagement = EthereumManagement
    private const val contractAddress: String = BuildConfig.ADDRESS_BALLOTS

    private const val REGISTER = "registerBallot"
    private const val RESULT = "resultOfBallot"
    private const val VOTE = "vote"

    fun registerBallot(_ballotData: BallotData
    ): Boolean? {
        ethereumManagement.setCredentials(BuildConfig.USER_PK)
        Log.d("REGISTER_VOTE_ADDRESS", contractAddress)
        val candidateList: List<Utf8String> = _ballotData.candidateNames.stream().map {
            Utf8String(it)
        }.toList()
        val candidateDynamicArray = DynamicArray(Utf8String::class.java, candidateList)
        val inputParams = listOf<Type<*>>(
            Bytes32(DatatypeConverter.parseHexBinary(_ballotData.did)),
            Uint256(_ballotData.publicKeyX),
            Uint256(_ballotData.publicKeyY),
            Utf8String(_ballotData.ballotId),
            candidateDynamicArray,
            Bool(_ballotData.isOfficial),
            Uint256(_ballotData.startTime),
            Uint256(_ballotData.endTime)
        )
        val outputParams = listOf<TypeReference<*>>(object: TypeReference<Bool>() {})
        return ethereumManagement.ethSendRaw(contractAddress, REGISTER, inputParams, outputParams) as Boolean?
    }

    fun resultOfBallot(ballotId: String): List<BigInteger> {
        ethereumManagement.setCredentials(BuildConfig.USER_PK)
        Log.d("RESULT_ADDRESS", contractAddress)
        val inputParams = listOf<Type<*>>(Utf8String(ballotId))
        val outputParams = listOf<TypeReference<*>>(object: TypeReference<DynamicArray<Uint>>() {})
        val decoded: List<Type<*>> = EthereumManagement.ethCall(BuildConfig.USER_ADDRESS, contractAddress, RESULT, inputParams, outputParams)!!
        val result: MutableList<BigInteger> = mutableListOf()
        (decoded[0].value as List<Uint>).forEach {
            result.add(it.value)
        }
        return result
    }

    fun vote(voteData: VoteData){
        ethereumManagement.setCredentials(BuildConfig.USER_PK)
        Log.d("VOTE_ADDRESS", contractAddress)
        val RList: List<Uint256> = voteData.R.stream().map {
            Uint256(it)
        }.toList()
        val bytearray = DatatypeConverter.parseHexBinary(voteData.m)
        val rListDynamicArray = DynamicArray(Uint256::class.java, RList)
        val inputParams = listOf<Type<*>>(
            Utf8String(voteData.ballotId),
            Bytes32(bytearray),
            Uint256(voteData.serverSig),
            Uint256(voteData.ownerSig),
            rListDynamicArray)
        val outputParams = listOf<TypeReference<*>>()
        ethereumManagement.ethSendRaw(contractAddress, VOTE, inputParams, outputParams, voteData.pseudoCredentials)
    }
}