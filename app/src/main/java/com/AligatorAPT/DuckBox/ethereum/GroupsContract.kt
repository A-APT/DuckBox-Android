package com.AligatorAPT.DuckBox.ethereum

import android.util.Log
import com.AligatorAPT.DuckBox.BuildConfig
import com.AligatorAPT.DuckBox.dto.ethereum.Requester
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.*
import org.web3j.abi.datatypes.generated.Bytes32
import javax.xml.bind.DatatypeConverter


object GroupsContract {

    private val ethereumManagement: EthereumManagement = EthereumManagement
    private const val contractAddress: String = BuildConfig.ADDRESS_GROUPS

    private const val REGISTER = "registerGroup"
    private const val DELETEGROUP = "deleteGroup"
    private const val REQUESTMEMBER = "requestMember"
    private const val APPROVEMEMBER = "approveMember"
    private const val EXITMEMBER = "exitMember"
    private const val APPROVEGROUP = "approveGroupAuthentication"
    private const val GETREQUESTERLIST = "getRequesterList"

    fun registerGroup(groupId: String, ownerDid: String): Boolean? { //only owner
        Log.d("ADDRESS", contractAddress)
        ethereumManagement.setCredentials(BuildConfig.USER_PK)
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(ownerDid))
        )
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSendRaw(contractAddress, REGISTER, inputParams, outputParams) as Boolean?
    }

    fun approveGroupAuthentication(groupId: String, approverDid: String): Boolean? {
        ethereumManagement.setCredentials(BuildConfig.USER_PK)
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(approverDid))
        )
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSendRaw(contractAddress, APPROVEGROUP, inputParams, outputParams) as Boolean?
    }

    fun deleteGroup(groupId: String, ownerDid: String): Boolean? { //only owner
        ethereumManagement.setCredentials(BuildConfig.USER_PK)
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(ownerDid))
        )
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSendRaw(contractAddress, DELETEGROUP, inputParams, outputParams) as Boolean?
    }

    fun requestMember(groupId: String, userDid: String, name: String, email: String): Boolean? {
        ethereumManagement.setCredentials(BuildConfig.USER_PK)
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(userDid)),
            Utf8String(name),
            Utf8String(email)
        )
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSendRaw(contractAddress, REQUESTMEMBER, inputParams, outputParams) as Boolean?
    }

    fun approveMember(groupId: String, approverDid: String, requesterDid:String): Boolean? {
        ethereumManagement.setCredentials(BuildConfig.USER_PK)
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(approverDid)),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(requesterDid))
        )
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSendRaw(contractAddress, APPROVEMEMBER, inputParams, outputParams) as Boolean?
    }

    fun exitMember(groupId: String, requesterDid: String): Boolean? {
        ethereumManagement.setCredentials(BuildConfig.USER_PK)
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(requesterDid))
        )
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSendRaw(contractAddress, EXITMEMBER, inputParams, outputParams) as Boolean?
    }

    fun getRequesterList(groupId: String): List<Requester> {
        val inputParams = listOf<Type<*>>(
            Utf8String(groupId)
        )
        val outputParams = listOf<TypeReference<*>>(object: TypeReference<DynamicArray<Bytes32>>() {}, object: TypeReference<DynamicArray<Bool>>() {}, object: TypeReference<DynamicArray<Utf8String>>() {}, object: TypeReference<DynamicArray<Utf8String>>() {})

        val decoded: List<Type<*>> = ethereumManagement.ethCall(BuildConfig.USER_ADDRESS, contractAddress, GETREQUESTERLIST, inputParams, outputParams)!!

        val did: MutableList<String> = mutableListOf()
        val isValid: MutableList<Boolean> = mutableListOf()
        val name: MutableList<String> = mutableListOf()
        val email: MutableList<String> = mutableListOf()

        (decoded[0].value as List<Bytes32>).forEach {
            did.add(DatatypeConverter.printHexBinary(it.value))
        }
        (decoded[1].value as List<Bool>).forEach {
            isValid.add(it.value)
        }
        (decoded[2].value as List<Utf8String>).forEach {
            name.add(it.value)
        }
        (decoded[3].value as List<Utf8String>).forEach {
            email.add(it.value)
        }

        val result: MutableList<Requester> = mutableListOf()

        for( i in 1 until did.size){
            result.add(
                Requester(
                    did = did[i],
                    isValid = isValid[i],
                    name = name[i],
                    email = email[i]
                )
            )
        }

        return result
    }
}
