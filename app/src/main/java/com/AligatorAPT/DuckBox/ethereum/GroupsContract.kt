package com.AligatorAPT.DuckBox.ethereum

import android.util.Log
import com.AligatorAPT.DuckBox.BuildConfig
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Bytes32
import org.web3j.utils.Numeric

object GroupsContract {

    private val ethereumManagement: EthereumManagement = EthereumManagement
    private const val contractAddress: String = BuildConfig.ADDRESS_GROUPS

    private const val REGISTER = "registerGroup"
    private const val DELETEGROUP = "deleteGroup"
    private const val REQUESTMEMBER = "requestMember"
    private const val APPROVEMEMBER = "approveMember"
    private const val EXITMEMBER = "exitMember"
    private const val APPROVEGROUP = "approveGroupAuthentication"

    fun registerGroup(groupId: String, ownerDid: String): Boolean? { //only owner
        Log.d("ADDRESS", contractAddress)
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(Numeric.hexStringToByteArray(EthereumManagement.asciiToHex(ownerDid)))
        )
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSend(GanacheAddress.GROUP_OWNER, contractAddress, REGISTER, inputParams, outputParams) as Boolean?
    }

    fun testFunction(groupId: String, ownerDid: String): Boolean? { //only owner
        Log.d("ADDRESS", contractAddress)
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(Numeric.hexStringToByteArray(EthereumManagement.asciiToHex(ownerDid)))
        )
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSend(GanacheAddress.GROUP_OWNER, contractAddress, "testFunction2", inputParams, outputParams) as Boolean?
    }

    fun approveGroupAuthentication1(groupId: String, approverDid: String): Boolean? {
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(Numeric.hexStringToByteArray(EthereumManagement.asciiToHex(approverDid)))
        )
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSend(GanacheAddress.APPROVER1, contractAddress, APPROVEGROUP, inputParams, outputParams) as Boolean?
    }

    fun approveGroupAuthentication2(groupId: String, approverDid: String): Boolean? {
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(Numeric.hexStringToByteArray(EthereumManagement.asciiToHex(approverDid)))
        )
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSend(GanacheAddress.APPROVER2, contractAddress, APPROVEGROUP, inputParams, outputParams) as Boolean?
    }

    fun deleteGroup(groupId: String, ownerDid: String): Boolean? { //only owner
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(Numeric.hexStringToByteArray(EthereumManagement.asciiToHex(ownerDid)))
        )
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSend(GanacheAddress.GROUP_OWNER, contractAddress, DELETEGROUP, inputParams, outputParams) as Boolean?
    }

    fun requestMember(groupId: String, userDid: String): Boolean? {
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(Numeric.hexStringToByteArray(EthereumManagement.asciiToHex(userDid)))
        )
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSend(GanacheAddress.USER1, contractAddress, REQUESTMEMBER, inputParams, outputParams) as Boolean?
    }

    fun approveMember1(groupId: String, approverDid: String, requesterDid:String): Boolean? {
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(Numeric.hexStringToByteArray(EthereumManagement.asciiToHex(approverDid))),
            Bytes32(Numeric.hexStringToByteArray(EthereumManagement.asciiToHex(requesterDid)))
        )
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSend(GanacheAddress.APPROVER1, contractAddress, APPROVEMEMBER, inputParams, outputParams) as Boolean?
    }

    fun approveMember2(groupId: String, approverDid: String, requesterDid:String): Boolean? {
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(Numeric.hexStringToByteArray(EthereumManagement.asciiToHex(approverDid))),
            Bytes32(Numeric.hexStringToByteArray(EthereumManagement.asciiToHex(requesterDid)))
        )
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSend(GanacheAddress.APPROVER2, contractAddress, APPROVEMEMBER, inputParams, outputParams) as Boolean?
    }

    fun exitMember(groupId: String, requesterDid: String): Boolean? {
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(Numeric.hexStringToByteArray(EthereumManagement.asciiToHex(requesterDid)))
        )
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSend(GanacheAddress.USER1, contractAddress, EXITMEMBER, inputParams, outputParams) as Boolean?
    }
}
