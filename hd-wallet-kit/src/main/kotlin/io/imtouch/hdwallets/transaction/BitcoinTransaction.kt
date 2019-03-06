package io.imtouch.hdwallets.transaction

import org.kethereum.model.ECKeyPair
import org.kethereum.model.SignedTransaction
import java.lang.RuntimeException

class BitcoinTransaction {

    fun signTransaction(ecKeyPair: ECKeyPair, transactionHex: String): SignedTransaction {
        throw RuntimeException("暂未实现")
    }

}