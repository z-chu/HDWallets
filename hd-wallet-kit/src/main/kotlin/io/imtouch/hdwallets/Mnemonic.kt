package io.imtouch.hdwallets

import org.kethereum.bip39.entropyToMnemonic
import org.kethereum.bip39.generateMnemonic
import org.kethereum.bip39.model.MnemonicWords
import org.kethereum.bip39.toSeed
import org.kethereum.bip39.validate
import org.kethereum.bip39.wordlists.WORDLIST_ENGLISH

object Mnemonic {

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
    fun generate(strength: Strength = Strength.Default): MnemonicWords {
        return MnemonicWords(generatePhrase(strength))
    }


    fun generateWords(strength: Strength = Strength.Default): List<String> {
        return generatePhrase(strength).split(" ")
    }

    fun generatePhrase(strength: Strength = Strength.Default) = generateMnemonic(strength.value, WORDLIST_ENGLISH)

    /**
     * Convert entropy data to mnemonic word list.
     */
    fun toMnemonic(entropy: ByteArray): List<String> {
        if (entropy.isEmpty()) throw EmptyEntropyException("Entropy is empty.")
        return entropyToMnemonic(entropy, WORDLIST_ENGLISH).split(" ")
    }

    /**
     * Convert mnemonic keys to seed
     */
    fun toSeed(mnemonicKeys: List<String>): ByteArray {
        check(mnemonicKeys)
        return MnemonicWords(mnemonicKeys).toSeed().seed
    }


    /**
     * Validate mnemonic keys
     */
    fun validate(mnemonicKeys: List<String>): Boolean {
        return MnemonicWords(mnemonicKeys).validate(WORDLIST_ENGLISH)
    }


    /**
     * Check mnemonic keys
     */
    fun check(mnemonicKeys: List<String>): MnemonicWords {
        if (mnemonicKeys.size !in (12..24 step 3)) {
            throw InvalidMnemonicCountException("Count: ${mnemonicKeys.size}")
        }

        val wordsList = WORDLIST_ENGLISH
        val mnemonicWords = MnemonicWords(mnemonicKeys)
        if (!mnemonicWords.validate(wordsList)) {
            throw InvalidMnemonicKeyException("Invalid word: $mnemonicKeys")
        }
        return mnemonicWords
    }

    open class MnemonicException(message: String) : Exception(message)

    class EmptyEntropyException(message: String) : MnemonicException(message)

    class InvalidMnemonicCountException(message: String) : MnemonicException(message)

    class InvalidMnemonicKeyException(message: String) : MnemonicException(message)

}
