package io.imtouch.hdwallets

import io.imtouch.hdwallets.address.BitcoinAddressCreator
import io.imtouch.hdwallets.address.EthereumAddressCreator
import io.imtouch.hdwallets.validators.ETHAddressValidator
import org.bitcoinj.params.MainNetParams
import org.junit.Assert
import org.junit.Test
import org.kethereum.bip32.model.ExtendedKey
import org.kethereum.bip32.model.XPriv
import org.kethereum.bip32.toExtendedKey

class ETHAddressValidatorTest {

    @Test
    fun testValidETHAddress() {
        val invalidAddresses = arrayOf(
            "shortstr",
            "longstrlongstrlongstrlongstrlongstrlongstrlongstrlongstrlongstr",
            "0x023e64d5e4178e652239729a80fbaedde1a41d7c070a55d22fcc6b1671a83e0ea2",
            "0x7e5b07659b64423bb86a5d8fcbb92751288fd91655f7b29eb0c9b752a9773672",
            "0x04F811e3e8979Fd0f597eA876f8FEaFafDf2892cs",
            "75d88350f25f5DcA02BC993B883b6d6934ED09eFsv"
        )
        for (address in invalidAddresses) {
            Assert.assertEquals(ETHAddressValidator.isValidAddress(address), false)
        }

        val validAddresses = arrayOf(
            "0x04F811e3e8979Fd0f597eA876f8FEaFafDf2892c",
            "0x75d88350f25f5DcA02BC993B883b6d6934ED09eF",
            "0x95e0DBc710caAEd6157133070e07150528f63C2c",
            "0xbe81248280609b825fB898b56c930F3C237e4C4D",
            "0xeC6a40f8E2800e496b5D7845A9D1405151079710",
            "0xF6bDB5a9E9cc8198EFCb8fcf8aB0a73ff7171e3A",
            "0x0f4ed67c8a4b501dC52870C146960406c9e382b0",
            "c49fC8486E44C83fC60013391E1f3E341c2DAB2b",
            "0BC719Ad03d6A0Aae88430B1cfa09F61c84E92f1",
            "fE59be66AfC08cF6E641a5160eF0d9122A16713A",
            "8A7c22e2654B90D1cDa40E7492cBADe97A9adA43",
            "0xac342eD914CBBd4705F2939e4645B9C12Be0Bf1f",
            "0xb52b0061b47671bf2fd8972cfc0335b043a1c22c"
        )

        for (address in validAddresses) {
            Assert.assertEquals(ETHAddressValidator.isValidAddress(address), true)
        }
    }

    @Test
    fun testFromPriv() {
        var toExtendedKey =
            XPriv("xpub6CS7LxgrqzYE9BtiMJMXWvGzBNFp6y5PcqLETTnQF1RXHp8jqkdqwo1KU8ThFNKeQsD7hyMUjC2qjQ3vHgKPRn3zcUExYF1dPMeVjd5iUbN")
                .toExtendedKey()


        checkFromPriv(toExtendedKey,"m/0/0","0x26B8F6daa5518a86D5f9291EBBfa29113bF00e6d")
        checkFromPriv(toExtendedKey,"m/0/1","0x624163A2467779564E556D34D8ee23338beB7FDe")
        checkFromPriv(toExtendedKey,"m/0/2","0x34C2512E9744B8B045bacd31B913bc67a9aFA75B")
        checkFromPriv(toExtendedKey,"m/0/3","0x0CeAeA7cf437A663B9522C17902C3dAEB7D68b5c")
        checkFromPriv(toExtendedKey,"m/0/4","0x3b18E713A55De3E91229a99b36778d5C79CD1537")
        checkFromPriv(toExtendedKey,"m/0/5","0x4B020576fb3582427bef5b3e7De2932a706204b5")
        checkFromPriv(toExtendedKey,"m/0/6","0x27a55D5fC6672537cc9bc6716754F6E78741Bfdb")
        checkFromPriv(toExtendedKey,"m/0/7","0xE97b7529aE393b6461eC00dD5CCdE3B831793C7f")
        checkFromPriv(toExtendedKey,"m/0/8","0x62A5bb118BCacC968c722aE76Fb2F610f6B3Edb4")
        checkFromPriv(toExtendedKey,"m/0/9","0x0c921db670b012D0823EaD30C2d514Bd0e4915ae")

         toExtendedKey =
            XPriv("xprv9ySkwT9y1cyvvhpFFGpX9nLFdLRKhWMYFcQdf5NngftYR1obJDKbPzgqcsmHiGaP42wLUaCxJ2Wy7P9nm6TjK5DbzqQHGQn4Kj15KrTPos9")
                .toExtendedKey()

        checkFromPriv(toExtendedKey,"m/0/0","0x26B8F6daa5518a86D5f9291EBBfa29113bF00e6d")
        checkFromPriv(toExtendedKey,"m/0/1","0x624163A2467779564E556D34D8ee23338beB7FDe")
        checkFromPriv(toExtendedKey,"m/0/2","0x34C2512E9744B8B045bacd31B913bc67a9aFA75B")
        checkFromPriv(toExtendedKey,"m/0/3","0x0CeAeA7cf437A663B9522C17902C3dAEB7D68b5c")
        checkFromPriv(toExtendedKey,"m/0/4","0x3b18E713A55De3E91229a99b36778d5C79CD1537")
        checkFromPriv(toExtendedKey,"m/0/5","0x4B020576fb3582427bef5b3e7De2932a706204b5")
        checkFromPriv(toExtendedKey,"m/0/6","0x27a55D5fC6672537cc9bc6716754F6E78741Bfdb")
        checkFromPriv(toExtendedKey,"m/0/7","0xE97b7529aE393b6461eC00dD5CCdE3B831793C7f")
        checkFromPriv(toExtendedKey,"m/0/8","0x62A5bb118BCacC968c722aE76Fb2F610f6B3Edb4")
        checkFromPriv(toExtendedKey,"m/0/9","0x0c921db670b012D0823EaD30C2d514Bd0e4915ae")
    }

   private fun checkFromPriv(extendedKey: ExtendedKey,pathString: String,addressString: String) {
        val toExtendedKey =extendedKey
                .generateChildKey(pathString)

        val fromECKeyPair = EthereumAddressCreator().fromECKeyPair(toExtendedKey.keyPair)
        Assert.assertEquals(fromECKeyPair,addressString.toLowerCase())

    }

}