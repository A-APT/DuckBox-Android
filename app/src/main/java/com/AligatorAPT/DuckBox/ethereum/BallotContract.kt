package com.AligatorAPT.DuckBox.ethereum

import com.AligatorAPT.DuckBox.BuildConfig
import com.AligatorAPT.DuckBox.dto.ethereum.Candidate
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.DynamicArray
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.Utf8String
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

    fun close(ballotId: String, totalNum: Int): String? {
        val inputParams = listOf<Type<*>>(Utf8String(ballotId), Uint256(totalNum.toLong()))
        val outputParams = listOf<TypeReference<*>>()
        return EthereumManagement.ethCall(contractAddress, CLOSE, inputParams, outputParams) as String?
    }

    fun resultOfBallot(ballotId: String):  ArrayList<Candidate>? {
        val inputParams = listOf<Type<*>>(Utf8String(ballotId))
        val outputParams = listOf<TypeReference<*>>()
        return EthereumManagement.ethCall(contractAddress, RESULT, inputParams, outputParams) as ArrayList<Candidate>?
    }
}
