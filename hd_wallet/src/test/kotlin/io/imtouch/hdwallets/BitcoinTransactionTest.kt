package io.imtouch.hdwallets

import io.imtouch.hdwallets.address.BitcoinAddressCreator
import io.imtouch.hdwallets.transaction.BitcoinTransaction
import io.imtouch.hdwallets.validators.BitcoinAddressValidator
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.params.TestNet3Params
import org.junit.Assert
import org.junit.Test
import org.kethereum.bip39.model.MnemonicWords

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

    @Test
    fun bitcoinIllegalAddress() {
        Assert.assertEquals(
            BitcoinAddressValidator.isValidAddress("cUQ2Xzvmyn8JpvoBpQ2jEBWBgGjAS2A4fQSigWYD3B1KHKo6box7"),
            false
        )
        Assert.assertEquals(
            BitcoinAddressValidator.isValidAddress("03add289da9f31e1d468c99e2d83ebe1fd6f403bad9928177011a9ffbb6d70b73a"),
            false
        )
        Assert.assertEquals(BitcoinAddressValidator.isValidAddress("0xEDa145386Ae0e6990318c94b4529CBe8F0B5b21C"), false)
        Assert.assertEquals(BitcoinAddressValidator.isValidAddress("n1fmDsaTWcWdDZvr5vgia4JHascTen6Xy4",MainNetParams.get()), false)
    }

    @Test
    fun bitcoinCorrectAddress() {
        BitcoinAddressValidator.isValidAddress("1BcUmJd8XxFqhrji5ZBF2Dbxkj3pDr3dqt", MainNetParams.get())
        BitcoinAddressValidator.isValidAddress("15Ag5i8SjRuJhQnqUpJtkeCk7DogpKBG1A", MainNetParams.get())
        BitcoinAddressValidator.isValidAddress("16ykMnFhBJWpxsVju8LD91CuCaK3faFWzy", MainNetParams.get())
        BitcoinAddressValidator.isValidAddress("n1fmDsaTWcWdDZvr5vgia4JHascTen6Xy4", TestNet3Params.get())
        BitcoinAddressValidator.isValidAddress("mitF3CY8vY8uNdb9pKrDyLt74S52q8Nehk", TestNet3Params.get())
        BitcoinAddressValidator.isValidAddress("mvBXH1Nh1xehRRXaWvym6LrYT9YVkXLB3T", TestNet3Params.get())
    }
}
