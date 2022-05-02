package com.AligatorAPT.DuckBox.dto.ethereum

import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.generated.Bytes32

data class Requester(
    var did: Bytes32,
    var isValid: Bool
)
