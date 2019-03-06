package io.imtouch.hdwallets

import io.imtouch.hdwallets.transaction.EthereumTransaction
import org.junit.Assert
import org.junit.Test
import org.kethereum.bip32.model.Seed
import org.kethereum.bip39.model.MnemonicWords
import org.kethereum.crypto.signMessage
import org.kethereum.crypto.toAddress
import org.kethereum.extensions.hexToBigInteger
import org.kethereum.functions.*

class EthereumTransactionTest {
    private val hdWalletMainNet = HDWallet(MnemonicWords("suspect napkin chief drill immense tackle hedgehog repair puzzle delay genre soap drastic hockey verb"), 60)
    @Test
    fun ethereumTransaction() {
        val generateKey = hdWalletMainNet.generateKey(0, 0, 0)
        Assert.assertEquals(generateKey.keyPair.toAddress().hex.hexToBigInteger(),"0x04F811e3e8979Fd0f597eA876f8FEaFafDf2892c".hexToBigInteger())
        val signedTransaction = EthereumTransaction().signTransaction(
            generateKey.keyPair,
            "e4808503b9aca0008252089404f811e3e8979fd0f597ea876f8feafafdf2892c80801c8080"
        )
        val encodeRLP = signedTransaction.encodeRLP()
        val toHexString = encodeRLP.toHexString()
        Assert.assertEquals(toHexString, "f864808503b9aca0008252089404f811e3e8979fd0f597ea876f8feafafdf2892c80801ba0555d41f84e7098213e9b819d534209dd913e8e492458adab45c4b934c0ba5922a0343d2b600347652ed492c3014eff6993c5d9f04dfc875fd0db881c5ed4633f75")
        val tokenTransfer = signedTransaction.transaction.isTokenTransfer()
        Assert.assertEquals(tokenTransfer, false)
    }

    @Test
    fun ethereumTokenTransaction() {
        val generateKey = hdWalletMainNet.generateKey(0, 0, 0)
        Assert.assertEquals(generateKey.keyPair.toAddress().hex.hexToBigInteger(),"0x04F811e3e8979Fd0f597eA876f8FEaFafDf2892c".hexToBigInteger())
        val signedTransaction = EthereumTransaction().signTransaction(
            generateKey.keyPair,
            "f86980850306dc420082ea6094ff603f43946a3a28df5e6a73172555d8c8b0238680b844a9059cbb00000000000000000000000004f811e3e8979fd0f597ea876f8feafafdf2892c00000000000000000000000000000000000000000000000000000000000000001c8080"
        )
        val encodeRLP = signedTransaction.encodeRLP()
        val toHexString = encodeRLP.toHexString()
        Assert.assertEquals(toHexString, "f8a980850306dc420082ea6094ff603f43946a3a28df5e6a73172555d8c8b0238680b844a9059cbb00000000000000000000000004f811e3e8979fd0f597ea876f8feafafdf2892c00000000000000000000000000000000000000000000000000000000000000001ba020c64cdd08b622c7da50dea989da49e03706dee7b18b4cbafc77e430e103d93ea03e04822e49c59c54bb50f9365f9c7b7147f1ecd2789b0899c9b99bda24b4956e")

        val tokenTransfer = signedTransaction.transaction.isTokenTransfer()
        Assert.assertEquals(tokenTransfer, true)


    }
}