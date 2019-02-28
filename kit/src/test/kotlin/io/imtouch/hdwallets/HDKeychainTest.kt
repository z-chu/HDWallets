package io.imtouch.hdwallets

import org.junit.Assert
import org.junit.Test

class HDKeychainTest {

    val seed =
        "6908630f564bd3ca9efb521e72da86727fc78285b15decedb44f40b02474502ed6844958b29465246a618b1b56b4bdffacd1de8b324159e0f7f594c611b0519d".hexStringToByteArray()
    val hdKeyManager = HDKeychain(seed)

    @Test
    fun getKeyByPath_Bip32() {
        val path32 = "m/0"
        val hdKey = hdKeyManager.getKeyByPath(path32)

        Assert.assertEquals(path32, hdKey.toString())
    }

    @Test
    fun getKeyByPath_Bip44() {
        val path44 = "m/44'/0'/0'/0"
        val hdKey = hdKeyManager.getKeyByPath(path44)

        Assert.assertEquals(path44, hdKey.toString())
    }

    @Test(expected = NumberFormatException::class)
    @Throws(Exception::class)
    fun getPrivateKeyByPath_invalidPath() {
        val path32 = "m/0/b"
        val hdKey = hdKeyManager.getKeyByPath(path32)
    }

}
