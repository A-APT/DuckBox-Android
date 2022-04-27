package com.AligatorAPT.DuckBox.ethereum

object GanacheAddress {
    //ganache user
    var signUpCount = 0

    val addressList = listOf<String>(
        "0x2D826299A991Bc3004E2aABf83d171C266af72c3", //group owner
        "0x6d8CA8947651b08fCBFcf43da31B083E53c27E78", //approver1
        "0x229aC6eF0c58c6eb33b12c989914543210176896", //approver2
        "0x44D11C7c4D7563715c45060fd4d01aDc20980Ccc", //user1
        "0x80aae15309D99BB79e033d5000E109a910DF936e", //user2
    )

    const val CONTRACT_OWNER = "0x16188309168f9a0619F3820dd44B4d4be1FeA321"
    const val GROUP_OWNER = "0x2D826299A991Bc3004E2aABf83d171C266af72c3"
    const val APPROVER1 = "0x6d8CA8947651b08fCBFcf43da31B083E53c27E78"
    const val APPROVER2 = "0x229aC6eF0c58c6eb33b12c989914543210176896"
    const val USER1 = "0x44D11C7c4D7563715c45060fd4d01aDc20980Ccc"
    const val USER2 = "0x80aae15309D99BB79e033d5000E109a910DF936e"
}
