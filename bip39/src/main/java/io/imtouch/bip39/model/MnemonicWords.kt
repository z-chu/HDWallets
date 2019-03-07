package io.imtouch.bip39.model

inline class MnemonicWords(val words: List<String>) {
    constructor(phrase: String) : this(phrase.split(" "))
    constructor(phrase: Array<String>) : this(phrase.toList())

    override fun toString() = words.joinToString(" ")
}