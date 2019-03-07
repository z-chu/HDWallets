package io.imtouch.hdwallets

import io.imtouch.hdwallets.address.BitcoinAddressCreator
import io.imtouch.hdwallets.transaction.BitcoinTransaction
import io.imtouch.hdwallets.transaction.EthereumTransaction
import org.bitcoinj.params.TestNet3Params
import org.junit.Assert
import org.junit.Test
import org.kethereum.bip39.model.MnemonicWords
import org.kethereum.crypto.toAddress
import org.kethereum.extensions.hexToBigInteger
import org.kethereum.functions.encodeRLP
import org.kethereum.functions.isTokenTransfer

class BitcoinTransactionTest {
    private val hdWalletMainNet = HDWallet(
        MnemonicWords("say tongue select oil blossom pond parent orphan crater sadness position coin"),
        1
    )

    @Test
    fun bitcoinTransaction() {
        val generateKey = hdWalletMainNet.generateKey(0, 0, 0)


        Assert.assertEquals(
            BitcoinAddressCreator(TestNet3Params.get()).fromECKeyPair(generateKey.keyPair),
            "mrmejCPpCUBroPn2XqPU2LpZ9jaDrmQZQr"
        )

        val signedTransaction = BitcoinTransaction(TestNet3Params.get()).signTransaction(
            generateKey.keyPair,
            "01000000017335aed204417492dadd3fdecabcd2f80c35cc374a47f4162a06f93176308aaf010000006a47304402204d666ca4c7234536b9aadfd0eb0ec3b2dca61acc2f57a6b0c9672661b760990602202ffe635d91effa77247e0d46212c84981be2bd654778929e7bd5736e3e4da60701210254dec37f0858dd993798f8b31ba912eb3cee803ac4209596cc79c804a2f3c201ffffffff0210270000000000001976a91415c5e0965754cd540c719aac3e52d36b2d9a815288ac28cfb500000000001976a91415c5e0965754cd540c719aac3e52d36b2d9a815288ac00000000"
        )


        val toHexString = signedTransaction.signedTx.toHexString()
        Assert.assertEquals(
            toHexString,
            "01000000017335aed204417492dadd3fdecabcd2f80c35cc374a47f4162a06f93176308aaf010000006a47304402204d666ca4c7234536b9aadfd0eb0ec3b2dca61acc2f57a6b0c9672661b760990602202ffe635d91effa77247e0d46212c84981be2bd654778929e7bd5736e3e4da60701210254dec37f0858dd993798f8b31ba912eb3cee803ac4209596cc79c804a2f3c201ffffffff0210270000000000001976a91415c5e0965754cd540c719aac3e52d36b2d9a815288ac28cfb500000000001976a91415c5e0965754cd540c719aac3e52d36b2d9a815288ac00000000"
        )
    }

}