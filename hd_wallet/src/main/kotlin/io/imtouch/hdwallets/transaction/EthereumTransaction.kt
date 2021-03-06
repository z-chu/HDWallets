package io.imtouch.hdwallets.transaction

import org.kethereum.crypto.signMessage
import org.kethereum.functions.*
import org.kethereum.functions.rlp.RLPList
import org.kethereum.functions.rlp.decodeRLP
import org.kethereum.model.Address
import org.kethereum.model.ECKeyPair
import org.kethereum.model.SignedTransaction
import org.kethereum.model.Transaction
import org.walleth.khex.hexToByteArray
import java.math.BigInteger

class EthereumTransaction {

    fun decodeTransactionHex(transactionHex: String): Transaction? {
        val decodeRLP = transactionHex.hexToByteArray().decodeRLP()
        return if (decodeRLP is RLPList) {
            decodeRLP.toTransaction()
        } else {
            null
        }

    }


    fun signTransaction(ecKeyPair: ECKeyPair, transactionHex: String): SignedTransaction {
        val transactionByteArray = transactionHex.hexToByteArray()
        val transaction = (transactionByteArray.decodeRLP() as RLPList).toTransaction()!!
        val signMessage = ecKeyPair.signMessage(transaction.encodeRLP())
        return SignedTransaction(transaction, signMessage)
    }
}

fun Transaction.checkSingle(toAddress: String, amount: BigInteger): Boolean {
    val address: Address?
    val value: BigInteger
    if (isTokenTransfer()) {
        address = getTokenTransferTo()
        value = getTokenTransferValue()
    } else {
        address = this.to
        value = this.value
    }
    return address?.equals(Address(toAddress)) ?: false && value.compareTo(amount) == 0
}