package com.AligatorAPT.DuckBox.ethereum

import android.util.Log
import com.AligatorAPT.DuckBox.BuildConfig
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Type
import org.web3j.crypto.*
import org.web3j.protocol.admin.Admin
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.core.methods.response.EthCall
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.http.HttpService
import org.web3j.tx.RawTransactionManager
import java.io.File
import java.math.BigInteger

object EthereumManagement {

    private const val ETH_NETWORK = BuildConfig.ETH_NETWORK_LOCAL
    //private val web3j = Web3j.build(HttpService(ETH_NETWORK))
    private val web3j = Admin.build(HttpService(ETH_NETWORK))
    private var credentials: Credentials? = null

    private val gasPrice: BigInteger = web3j.ethGasPrice().sendAsync().get().gasPrice
    private val gasLimit: BigInteger = BigInteger.valueOf(6700000) // gasLimit

    fun createNewCredentials(password: String): Credentials {
        val keyPair: ECKeyPair = Keys.createEcKeyPair()
        return Credentials.create(keyPair)
    }

    fun createWallet(password: String): WalletFile {
        val keyPair: ECKeyPair = Keys.createEcKeyPair()
        return Wallet.createLight(password, keyPair)
    }

    fun createWallet(password: String, path: String): String {
        val walletFileName: String = WalletUtils.generateLightNewWalletFile(password, File(path))
        val walletPath: String = File(path, walletFileName).absolutePath
        credentials = WalletUtils.loadCredentials(password, walletPath)
        return walletPath
    }

    fun loadCredentials(password: String, walletPath: String) {
        credentials = WalletUtils.loadCredentials(password, walletPath)
    }

    fun setCredentials(password: String){
        credentials = Credentials.create(password)
    }

    fun ethCall(
        userAddress: String,
        contractAddress: String,
        functionName: String,
        inputParams: List<Type<*>>,
        outputParams: List<TypeReference<*>>
    ): List<Type<*>>? {

        // generate function
        val function = org.web3j.abi.datatypes.Function(functionName, inputParams, outputParams)
        val encodedFunction = FunctionEncoder.encode(function)

        // call function
        // createFunctionCallTransaction BigInteger
        val transaction = Transaction.createEthCallTransaction(userAddress, contractAddress, encodedFunction)
        val ethCall: EthCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get()

        if (ethCall.hasError()){
            Log.e("EthException", ethCall.error.message)
        }

        // decode response
        val decode: List<Type<*>> = FunctionReturnDecoder.decode(ethCall.result, function.outputParameters)
//        Log.e("LIST:::","ethcCall result ${ethCall.result} / value: ${decode[0].value} / type: ${decode[0].typeAsString}")
        return decode.ifEmpty { null }
    }

    fun ethSend(
        userAddress:String,
        contractAddress: String,
        functionName: String,
        inputParams: List<Type<*>>,
        outputParams: List<TypeReference<*>>
    ): Any? {
        // * Have to unlock account

        // generate function
        val function = org.web3j.abi.datatypes.Function(functionName, inputParams, outputParams)
        val encodedFunction = FunctionEncoder.encode(function)

        // create raw transaction (:signed transaction)
        val transaction = Transaction.createEthCallTransaction(userAddress, contractAddress, encodedFunction)
        val ethSend: EthSendTransaction = web3j.ethSendTransaction(transaction).sendAsync().get()

        if (ethSend.hasError()){
            Log.e("EthException", ethSend.error.message)
        }

        // decode response
        val decode = FunctionReturnDecoder.decode(ethSend.result, function.outputParameters)
        return if (decode.size > 0) decode[0].value else null
    }

    /* use user account's credentials (default) */
    fun ethSendRaw(
        contractAddress: String,
        functionName: String,
        inputParams: List<Type<*>>,
        outputParams: List<TypeReference<*>>
    ): Any? {
        // * Have to unlock account

        // generate function
        val function = org.web3j.abi.datatypes.Function(functionName, inputParams, outputParams)
        val encodedFunction = FunctionEncoder.encode(function)

        // send raw transaction
        val manager = RawTransactionManager(web3j, credentials)
        val ethSend: EthSendTransaction = manager.sendTransaction(
            gasPrice,
            gasLimit,
            contractAddress, // to
            encodedFunction, // data
            BigInteger.ZERO // value
        )

        if (ethSend.hasError()){
            Log.e("EthException", ethSend.error.message)
        }

        // decode response
        val decode = FunctionReturnDecoder.decode(ethSend.result, function.outputParameters)
        return if (decode.size > 0) decode[0].value else null
    }

    /* to use pseudoCredentials */
    fun ethSendRaw(
        contractAddress: String,
        functionName: String,
        inputParams: List<Type<*>>,
        outputParams: List<TypeReference<*>>,
        credentials: Credentials /* credentials */
    ): Any? {
        // * Have to unlock account

        // generate function
        val function = org.web3j.abi.datatypes.Function(functionName, inputParams, outputParams)
        val encodedFunction = FunctionEncoder.encode(function)

        // send raw transaction
        val manager = RawTransactionManager(web3j, credentials)
        val ethSend: EthSendTransaction = manager.sendTransaction(
            gasPrice,
            gasLimit,
            contractAddress, // to
            encodedFunction, // data
            BigInteger.ZERO // value
        )

        if (ethSend.hasError()){
            throw Exception(ethSend.error.message)
        }

        // decode response
        val decode = FunctionReturnDecoder.decode(ethSend.result, function.outputParameters)
        return if (decode.size > 0) decode[0].value else null
    }

    fun getReceipt(transactionHash: String): TransactionReceipt? {
        val transactionReceipt: EthGetTransactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send()
        return if (transactionReceipt.transactionReceipt.isPresent) {
            transactionReceipt.result
        } else {
            null
        }
    }
}
