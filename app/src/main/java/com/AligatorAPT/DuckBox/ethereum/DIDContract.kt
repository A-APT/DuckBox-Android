package com.AligatorAPT.DuckBox.ethereum

import android.util.Log
import com.AligatorAPT.DuckBox.BuildConfig
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.generated.Bytes32

object DIDContract {

    private val ethereumManagement: EthereumManagement = EthereumManagement
    private const val contractAddress: String = BuildConfig.ADDRESS_DID

    private const val REGISTER = "registerId"
    private const val UNREGISTER = "removeId"

    fun registerDid(address: String, did: String): Boolean? {
        Log.d("ADDRESS", contractAddress)
        val inputParams = listOf<Type<*>>(
            Address(address),
            Bytes32(javax.xml.bind.DatatypeConverter.parseHexBinary(did))
        )
        val outputParams = listOf<TypeReference<*>>()
        return ethereumManagement.ethSend(GanacheAddress.CONTRACT_OWNER, contractAddress, REGISTER, inputParams, outputParams) as Boolean?
    }

    fun removeDid(address: String) {
        val inputParams = listOf<Type<*>>(Address(address))
        val outputParams = listOf<TypeReference<*>>()
        ethereumManagement.ethSend(GanacheAddress.CONTRACT_OWNER, contractAddress, UNREGISTER, inputParams, outputParams)
    }

    fun getOwner(): String? {
        val inputParams = listOf<Type<*>>()
        val outputParams = listOf<TypeReference<*>>(object: TypeReference<Address>() {})
        return ethereumManagement.ethCall(contractAddress, "owner", inputParams, outputParams) as String?
    }
}
