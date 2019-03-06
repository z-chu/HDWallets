package io.imtouch.hdwallets


import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.api.mockito.PowerMockito.doAnswer
import org.powermock.api.mockito.PowerMockito.mock
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.security.SecureRandom

@RunWith(PowerMockRunner::class)
@PrepareForTest(SecureRandom::class, Mnemonic::class)
@PowerMockIgnore("javax.crypto.*")

class MnemonicTest {

    private val mnemonic = Mnemonic(Wordlist.ofEnglish())

    @Test
    fun throwsOnWrongEntropySize() {
        Assert.assertEquals(Wordlist.ofEnglish().size, 2048)
        Assert.assertEquals(Wordlist.ofSpanish().size, 2048)
        Assert.assertEquals(Wordlist.ofFrench().size, 2048)
        Assert.assertEquals(Wordlist.ofItalian().size, 2048)
        Assert.assertEquals(Wordlist.ofJapanese().size, 2048)
        Assert.assertEquals(Wordlist.ofKorean().size, 2048)
        Assert.assertEquals(Wordlist.ofChineseSimplified().size, 2048)
        Assert.assertEquals(Wordlist.ofChineseTraditional().size, 2048)


    }

    private fun print(list: List<String>) {
        val sorted = list.sorted()
        for (s in sorted) {
            System.out.print("\"$s\",")
        }
        System.out.println("\n")

    }

    @Test
    fun toMnemonic_Success() {
        val entropy: ByteArray = hexStringToByteArray("7787bfe5815e1912a1ec409a56391109")
        val mnemonicWords = mnemonic.toMnemonic(entropy).joinToString(separator = " ")
        val expectedWords = "jealous digital west actor thunder matter marble marine olympic range dust banner"
        Assert.assertEquals(mnemonicWords, expectedWords)
    }

    @Test(expected = Mnemonic.EmptyEntropyException::class)
    @Throws(Exception::class)
    fun toMnemonic_EmptyEntropy() {
        val entropy: ByteArray = hexStringToByteArray("")
        mnemonic.toMnemonic(entropy)
    }

    @Test
    fun check_Success() {

        val mnemonicKeys = listOf(
            "jealous",
            "digital",
            "west",
            "actor",
            "thunder",
            "matter",
            "marble",
            "marine",
            "olympic",
            "range",
            "dust",
            "banner"
        )

        mnemonic.check(mnemonicKeys)
    }

    @Test(expected = Mnemonic.InvalidMnemonicCountException::class)
    fun check_WrongWordsCount() {

        val mnemonicKeys = listOf(
            "digital",
            "west",
            "actor",
            "thunder",
            "matter",
            "marble",
            "marine",
            "olympic",
            "range",
            "dust",
            "banner"
        )

        mnemonic.check(mnemonicKeys)
    }

    @Test(expected = IllegalArgumentException::class)
    fun check_InvalidMnemonicKey() {

        val mnemonicKeys = listOf(
            "jealous",
            "digitalll",
            "west",
            "actor",
            "thunder",
            "matter",
            "marble",
            "marine",
            "olympic",
            "range",
            "dust",
            "banner"
        )

        mnemonic.check(mnemonicKeys)
    }

    @Test
    fun toSeed_Success() {

        val mnemonicKeys = listOf(
            "jealous",
            "digital",
            "west",
            "actor",
            "thunder",
            "matter",
            "marble",
            "marine",
            "olympic",
            "range",
            "dust",
            "banner"
        )

        val seed = mnemonic.toSeed(mnemonicKeys)

        val expectedSeed =
            hexStringToByteArray("6908630f564bd3ca9efb521e72da86727fc78285b15decedb44f40b02474502ed6844958b29465246a618b1b56b4bdffacd1de8b324159e0f7f594c611b0519d")

        Assert.assertArrayEquals(seed, expectedSeed)
    }

    @Test(expected = Mnemonic.InvalidMnemonicCountException::class)
    fun toSeed_WrongWordsCount() {

        val mnemonicKeys = listOf(
            "digital",
            "west",
            "actor",
            "thunder",
            "matter",
            "marble",
            "marine",
            "olympic",
            "range",
            "dust",
            "banner"
        )

        mnemonic.toSeed(mnemonicKeys)
    }

    @Test(expected = IllegalArgumentException::class)
    fun toSeed_InvalidMnemonicKey() {

        val mnemonicKeys = listOf(
            "jealous",
            "digitalll",
            "west",
            "actor",
            "thunder",
            "matter",
            "marble",
            "marine",
            "olympic",
            "range",
            "dust",
            "banner"
        )

        mnemonic.check(mnemonicKeys)
    }

    @Test
    fun generate() {

        val entropy = hexStringToByteArray("7787bfe5815e1912a1ec409a56391109")
        val seed = ByteArray(128 / 8)
        val random = mock(SecureRandom::class.java)

        PowerMockito.whenNew(SecureRandom::class.java)
            .withNoArguments()
            .thenReturn(random)

        doAnswer {
            val arg1: ByteArray = it.arguments[0] as ByteArray
            for (i in 0 until entropy.size) {
                arg1[i] = entropy[i]
            }
        }.whenever(random).nextBytes(seed)

        val mnemonicKeys = mnemonic.toMnemonic(entropy).toTypedArray()

        val mnemonicKeysExpected = arrayOf(
            "jealous",
            "digital",
            "west",
            "actor",
            "thunder",
            "matter",
            "marble",
            "marine",
            "olympic",
            "range",
            "dust",
            "banner"
        )

        Assert.assertArrayEquals(mnemonicKeys, mnemonicKeysExpected)
    }

    private fun hexStringToByteArray(s: String): ByteArray {
        val len = s.length
        val data = ByteArray(len / 2)

        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(s[i], 16) shl 4) + Character.digit(s[i + 1], 16)).toByte()
            i += 2
        }

        return data
    }

}
