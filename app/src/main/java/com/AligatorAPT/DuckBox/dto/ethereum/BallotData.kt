package com.AligatorAPT.DuckBox.dto.ethereum

import java.math.BigInteger

data class BallotData(
    val did: String,
    val ballotId: String,
    val publicKeyX: BigInteger,
    val publicKeyY: BigInteger,
    val candidateNames: List<String>,
    val isOfficial: Boolean,
    val startTime: Long, // milliseconds
    val endTime: Long
) {
}