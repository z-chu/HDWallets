package io.imtouch.hdwallets

import io.imtouch.hdwallets.validators.ETHAddressValidator
import org.junit.Assert
import org.junit.Test

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
            "8A7c22e2654B90D1cDa40E7492cBADe97A9adA43"
        )

        for (address in validAddresses) {
            Assert.assertEquals(ETHAddressValidator.isValidAddress(address), true)
        }
    }
}