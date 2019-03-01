package io.imtouch.hdwallets

import org.kethereum.bip39.entropyToMnemonic
import org.kethereum.bip39.generateMnemonic
import org.kethereum.bip39.model.MnemonicWords
import org.kethereum.bip39.toSeed
import org.kethereum.bip39.wordlists.WORDLIST_ENGLISH
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import kotlin.experimental.and

class Mnemonic {

    enum class Strength(val value: Int) {
        Default(128),
        Low(160),
        Medium(192),
        High(224),
        VeryHigh(256)
    }

    /**
     * Generate mnemonic keys
     */
    fun generate(strength: Strength = Strength.Default): List<String> {
        return  generateMnemonic(strength.value, WORDLIST_ENGLISH).split(" ")
    }

    /**
     * Convert entropy data to mnemonic word list.
     */
    fun toMnemonic(entropy: ByteArray): List<String> {
        return entropyToMnemonic(entropy, WORDLIST_ENGLISH).split(" ")
    }


    /**
     * Convert mnemonic keys to seed
     */
    fun toSeed(mnemonicKeys: List<String>): ByteArray {
        return   MnemonicWords(mnemonicKeys).toSeed().seed
    }


    /**
     * Validate mnemonic keys
     */
    fun validate(mnemonicKeys: List<String>) {

        if (mnemonicKeys.size !in (12..24 step 3)) {
            throw InvalidMnemonicCountException("Count: ${mnemonicKeys.size}")
        }

        val wordsList = WORDLIST_ENGLISH

        for (mnemonic: String in mnemonicKeys) {
            if (!wordsList.contains(mnemonic))
                throw InvalidMnemonicKeyException("Invalid word: $mnemonic")
        }

    }




    open class MnemonicException(message: String) : Exception(message)

    class EmptyEntropyException(message: String) : MnemonicException(message)

    class InvalidMnemonicCountException(message: String) : MnemonicException(message)

    class InvalidMnemonicKeyException(message: String) : MnemonicException(message)

}
