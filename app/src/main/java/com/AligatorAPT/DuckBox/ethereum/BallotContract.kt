package com.AligatorAPT.DuckBox.ethereum

import com.AligatorAPT.DuckBox.BuildConfig
import com.AligatorAPT.DuckBox.dto.ethereum.Candidate
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.*
import org.web3j.abi.datatypes.generated.Bytes32
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger
import javax.xml.bind.DatatypeConverter
import kotlin.streams.toList

object BallotContract {

    private const val contractAddress: String = BuildConfig.ADDRESS_BALLOTS

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
        return EthereumManagement.ethSendRaw(contractAddress, REGISTER, inputParams, outputParams) as Boolean?
    }

    fun open(ballotId: String) {
        val inputParams = listOf<Type<*>>(Utf8String(ballotId))
        val outputParams = listOf<TypeReference<*>>()
        EthereumManagement.ethSendRaw(contractAddress, OPEN, inputParams, outputParams)
    }

    fun close(ballotId: String, totalNum: Int) {
        val inputParams = listOf<Type<*>>(Utf8String(ballotId), Uint256(totalNum.toLong()))
        val outputParams = listOf<TypeReference<*>>()
        EthereumManagement.ethCall(contractAddress, CLOSE, inputParams, outputParams)
    }

    fun resultOfBallot(ballotId: String): List<BigInteger> {
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
        R: ArrayList<BigInteger>
    ){
        val RList: List<Uint256> = R.stream().map {
            Uint256(it)
        }.toList()
        val rListDynamicArray = DynamicArray(Uint256::class.java, RList)
        val inputParams = listOf<Type<*>>(Utf8String(_ballotId), Utf8String(_m), Uint256(_serverSig), Uint256(_ownerSig), rListDynamicArray)
        val outputParams = listOf<TypeReference<*>>()
        EthereumManagement.ethSendRaw(contractAddress, VOTE, inputParams, outputParams)
    }
}
