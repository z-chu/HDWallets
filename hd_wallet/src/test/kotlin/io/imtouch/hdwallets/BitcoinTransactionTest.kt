package io.imtouch.hdwallets

import io.imtouch.hdwallets.address.BitcoinAddressCreator
import io.imtouch.hdwallets.transaction.BitcoinTransaction
import org.bitcoinj.core.Address
import org.bitcoinj.core.AddressFormatException
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

    @Test(expected = AddressFormatException::class)
    fun bitcoinIllegalAddress() {
        Address.fromBase58(MainNetParams.get(),"cUQ2Xzvmyn8JpvoBpQ2jEBWBgGjAS2A4fQSigWYD3B1KHKo6box7")
        Address.fromBase58(MainNetParams.get(),"03add289da9f31e1d468c99e2d83ebe1fd6f403bad9928177011a9ffbb6d70b73a")
        Address.fromBase58(MainNetParams.get(),"0xEDa145386Ae0e6990318c94b4529CBe8F0B5b21C")
        Address.fromBase58(MainNetParams.get(),"n1fmDsaTWcWdDZvr5vgia4JHascTen6Xy4")
    }

    @Test
    fun bitcoinCorrectAddress() {
        Address.fromBase58(MainNetParams.get(),"1BcUmJd8XxFqhrji5ZBF2Dbxkj3pDr3dqt")
        Address.fromBase58(MainNetParams.get(),"15Ag5i8SjRuJhQnqUpJtkeCk7DogpKBG1A")
        Address.fromBase58(MainNetParams.get(),"16ykMnFhBJWpxsVju8LD91CuCaK3faFWzy")
        Address.fromBase58(TestNet3Params.get(),"n1fmDsaTWcWdDZvr5vgia4JHascTen6Xy4")
        Address.fromBase58(TestNet3Params.get(),"mitF3CY8vY8uNdb9pKrDyLt74S52q8Nehk")
        Address.fromBase58(TestNet3Params.get(),"mvBXH1Nh1xehRRXaWvym6LrYT9YVkXLB3T")
    }
}