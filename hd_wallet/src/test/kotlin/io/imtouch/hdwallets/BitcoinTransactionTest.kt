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
        MnemonicWords("lock strategy teach now balance scout deer february just cheap blossom manual"),
        1
    )

    @Test
    fun bitcoinTransaction() {
        val generateKey = hdWalletMainNet.generateKey(0, 0, 0)


        Assert.assertEquals(
            BitcoinAddressCreator(TestNet3Params.get()).fromECKeyPair(generateKey.keyPair),
            "n3ZbETgZf1o17Gc9u3C1UUPZ51akDvHzQh"
        )


        val signedTransaction = BitcoinTransaction(TestNet3Params.get()).signTransaction(
            generateKey.keyPair,
            "0100000003e17995385b94ddccd59062c1d97b9fab3c9b88a65c528dbd032b160ec0dd6f650000000000ffffffffe17995385b94ddccd59062c1d97b9fab3c9b88a65c528dbd032b160ec0dd6f650100000000ffffffff9432f616fc454a499c6f0c38ebd44c22bf9a60cdb8db50bf983e3650608664580100000000ffffffff02a0860100000000001976a914f1d26ef9a98c66ba81c7a188ed00a5703ae13a2688ac3a725300000000001976a914f1d26ef9a98c66ba81c7a188ed00a5703ae13a2688ac00000000"
        )



        val toHexString = signedTransaction.signedTx.toHexString()
        Assert.assertEquals(
            toHexString,
            "0100000003e17995385b94ddccd59062c1d97b9fab3c9b88a65c528dbd032b160ec0dd6f65000000006a47304402204378d85811ccb3945d85fad4e7a709cad9e5761140d19171c255702fe1889eb802207d4da60edad0cf39215d8e598959b0e2295fa89ecb268c248ae162ca1ffda0d0012102a8ac6a04d12f3f1ac11d84b8d9f3406f7a04164a57df5a770c0bbf9df94ce143ffffffffe17995385b94ddccd59062c1d97b9fab3c9b88a65c528dbd032b160ec0dd6f65010000006b483045022100efeb725c15b344d052582cb5f13214b3ef4bb45114893ea92a4a9a947300888d02201d4b20219b3797eb8e8049538a1c6d1bb3340c8d2cbde50ffaa4cfff98d61060012102a8ac6a04d12f3f1ac11d84b8d9f3406f7a04164a57df5a770c0bbf9df94ce143ffffffff9432f616fc454a499c6f0c38ebd44c22bf9a60cdb8db50bf983e365060866458010000006b4830450221009b51322fd7ed5c404a91667b48ccacb833509f4cda7d9d7a38960d0c425990c4022019cb11b09b6349ede79d34a966bedba2420f3c450414babe16e5d5ef8a2ac702012102a8ac6a04d12f3f1ac11d84b8d9f3406f7a04164a57df5a770c0bbf9df94ce143ffffffff02a0860100000000001976a914f1d26ef9a98c66ba81c7a188ed00a5703ae13a2688ac3a725300000000001976a914f1d26ef9a98c66ba81c7a188ed00a5703ae13a2688ac00000000"
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
