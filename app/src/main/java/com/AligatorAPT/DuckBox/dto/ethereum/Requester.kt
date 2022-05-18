package com.AligatorAPT.DuckBox.dto.ethereum

import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Bytes32

data class Requester(
    var did: Bytes32,
    var isValid: Bool,
    var name: Utf8String,
    var email: Utf8String
)
