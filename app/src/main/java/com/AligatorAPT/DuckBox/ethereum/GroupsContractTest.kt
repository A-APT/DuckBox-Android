package com.AligatorAPT.DuckBox.ethereum

import android.util.Log
import com.AligatorAPT.DuckBox.BuildConfig
import com.AligatorAPT.DuckBox.dto.ethereum.Requester
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Bytes32

object GroupsContractTest {

    private val ethereumManagement: EthereumManagement = EthereumManagement
//    private const val contractAddress: String = BuildConfig.ADDRESS_GROUPS

    private const val REGISTER = "registerGroup"
    private const val DELETEGROUP = "deleteGroup"
    private const val REQUESTMEMBER = "requestMember"
    private const val APPROVEMEMBER = "approveMember"
    private const val EXITMEMBER = "exitMember"
    private const val APPROVEGROUP = "approveGroupAuthentication"
    private const val GETREQUESTERLIST = "getRequesterList"

    fun registerGroup(groupId: String, ownerDid: String): Boolean? { //only owner
//        Log.d("ADDRESS", contractAddress)
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(ownerDid))
        )
        val outputParams = listOf<TypeReference<*>>()
//        return ethereumManagement.ethSend(GanacheAddress.GROUP_OWNER, contractAddress, REGISTER, inputParams, outputParams) as Boolean?
        return null
    }

    fun approveGroupAuthentication(groupId: String, approverDid: String): Boolean? {
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(approverDid))
        )
        val outputParams = listOf<TypeReference<*>>()
//        return ethereumManagement.ethSend(GanacheAddress.APPROVER1, contractAddress, APPROVEGROUP, inputParams, outputParams) as Boolean?
        return null
    }

    fun approveGroupAuthentication2(groupId: String, approverDid: String): Boolean? {
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(approverDid))
        )
        val outputParams = listOf<TypeReference<*>>()
//        return ethereumManagement.ethSend(GanacheAddress.APPROVER2, contractAddress, APPROVEGROUP, inputParams, outputParams) as Boolean?
        return null
    }

    fun deleteGroup(groupId: String, ownerDid: String): Boolean? { //only owner
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(ownerDid))
        )
        val outputParams = listOf<TypeReference<*>>()
//        return ethereumManagement.ethSend(GanacheAddress.GROUP_OWNER, contractAddress, DELETEGROUP, inputParams, outputParams) as Boolean?
        return null
    }

    fun requestMember(groupId: String, userDid: String, name: String, email: String): Boolean? {
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(userDid)),
            Utf8String(name),
            Utf8String(email)
        )
        val outputParams = listOf<TypeReference<*>>()
//        return ethereumManagement.ethSend(GanacheAddress.USER1, contractAddress, REQUESTMEMBER, inputParams, outputParams) as Boolean?
        return null
    }

    fun approveMember(groupId: String, approverDid: String, requesterDid:String): Boolean? {
//        ethereumManagement.setCredentials(BuildConfig.USER_PK)
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(approverDid)),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(requesterDid))
        )
        val outputParams = listOf<TypeReference<*>>()
//        return ethereumManagement.ethSend(GanacheAddress.APPROVER1, contractAddress, APPROVEMEMBER, inputParams, outputParams) as Boolean?
        return null
    }

    fun approveMember2(groupId: String, approverDid: String, requesterDid:String): Boolean? {
//        ethereumManagement.setCredentials(BuildConfig.USER_PK)
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(approverDid)),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(requesterDid))
        )
        val outputParams = listOf<TypeReference<*>>()
//        return ethereumManagement.ethSend(GanacheAddress.APPROVER2, contractAddress, APPROVEMEMBER, inputParams, outputParams) as Boolean?
        return null
    }

    fun exitMember(groupId: String, requesterDid: String): Boolean? {
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(requesterDid))
        )
        val outputParams = listOf<TypeReference<*>>()
//        return ethereumManagement.ethSend(GanacheAddress.USER1, contractAddress, EXITMEMBER, inputParams, outputParams) as Boolean?
        return null
    }

    fun getRequesterList(groupId: String): ArrayList<Requester>? {
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId)
        )
        val outputParams = listOf<TypeReference<*>>()
//        return ethereumManagement.ethCall(GanacheAddress.GROUP_OWNER, contractAddress, GETREQUESTERLIST, inputParams, outputParams) as ArrayList<Requester>?
        return null
    }
}
