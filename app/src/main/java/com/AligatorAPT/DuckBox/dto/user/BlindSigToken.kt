package com.AligatorAPT.DuckBox.dto.user

data class BlindSigToken (
    val serverBSig: String,
    val ownerBSig: String
) // in radix 16