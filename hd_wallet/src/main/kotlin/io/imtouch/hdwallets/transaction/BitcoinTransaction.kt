package io.imtouch.hdwallets.transaction

import org.bitcoinj.core.ECKey
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.core.Transaction
import org.bitcoinj.crypto.TransactionSignature
import org.bitcoinj.script.ScriptBuilder
import org.kethereum.model.ECKeyPair
import org.walleth.khex.hexToByteArray


class BitcoinTransaction(private val networkParams: NetworkParameters) {

    fun decodeTransactionHex(transactionHex: String): Transaction {
        return Transaction(networkParams, transactionHex.hexToByteArray())
    }


    fun signTransaction(ecKeyPair: ECKeyPair, transactionHex: String): SignedTransaction {
        return signTransaction(ECKey.fromPrivate(ecKeyPair.privateKey.key), transactionHex)
    }


    fun signTransaction(ecKey: ECKey, transactionHex: String): SignedTransaction {
        val transaction = decodeTransactionHex(transactionHex)
        for (i in transaction.inputs.indices) {
            val input = transaction.getInput(i.toLong())
            val scriptPubKey = ScriptBuilder.createOutputScript(ecKey.toAddress(networkParams))
            val hash = transaction.hashForSignature(i, scriptPubKey, Transaction.SigHash.ALL, false)
            val ecSig = ecKey.sign(hash)
            val txSig = TransactionSignature(ecSig, Transaction.SigHash.ALL, false)
            if (scriptPubKey.isSentToRawPubKey) {
                input.scriptSig = ScriptBuilder.createInputScript(txSig, ecKey)
            } else {
                if (!scriptPubKey.isSentToAddress) {
                    return SignedTransaction(transaction, ByteArray(0))
                }
                input.scriptSig = ScriptBuilder.createInputScript(txSig, ecKey)
            }
        }
        return SignedTransaction(transaction, transaction.bitcoinSerialize())
    }

    class SignedTransaction(val transaction: Transaction, val signedTx: ByteArray)

}

