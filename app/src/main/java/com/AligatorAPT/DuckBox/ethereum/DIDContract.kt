package com.AligatorAPT.DuckBox.ethereum

import com.AligatorAPT.DuckBox.BuildConfig
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.Utf8String

object DIDContract {

    private val ethereumManagement: EthereumManagement = EthereumManagement
    private const val contractAddress: String = BuildConfig.ADDRESS_DID

    private const val REGISTER = "registerId"
    private const val UNREGISTER = "removeId"
    private const val GET = "getId"

    fun registerDid(did: String): Boolean? {
        val inputParams = listOf<Type<*>>(Utf8String(did))
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSendRaw(contractAddress, REGISTER, inputParams, outputParams) as Boolean?
    }

    fun removeDid(did: String) {
        val inputParams = listOf<Type<*>>(Utf8String(did))
        val outputParams = listOf<TypeReference<*>>()
        ethereumManagement.ethSendRaw(contractAddress, UNREGISTER, inputParams, outputParams)
    }

    fun getDid(did: String): String? {
        val inputParams = listOf<Type<*>>(Utf8String(did))
        val outputParams = listOf<TypeReference<*>>(object: TypeReference<Utf8String>() {})
        return ethereumManagement.ethCall(contractAddress, GET, inputParams, outputParams) as String?
    }

    fun getOwner(): String? {
        val inputParams = listOf<Type<*>>()
        val outputParams = listOf<TypeReference<*>>(object: TypeReference<Address>() {})
        return ethereumManagement.ethCall(contractAddress, "owner", inputParams, outputParams) as String?
    }
}
