package com.AligatorAPT.DuckBox.dto.ethereum

import org.web3j.crypto.Credentials
import java.math.BigInteger

data class VoteData(
    val ballotId: String,
    val m: String,
    val serverSig: BigInteger,
    val ownerSig: BigInteger,
    val R: ArrayList<BigInteger>,
    val pseudoCredentials: Credentials,
) {
}