package com.AligatorAPT.DuckBox.dto.user

data class BlindSigRequestDto (
    val targetId: String, // vote or survey id (ObjectID)
    val blindMessage: String, // blindMessage: BigInteger(blindMessage, 16)
)