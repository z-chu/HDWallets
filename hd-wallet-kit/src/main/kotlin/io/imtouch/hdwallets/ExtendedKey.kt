package io.imtouch.hdwallets

import org.kethereum.bip32.model.ExtendedKey
import org.kethereum.crypto.CryptoAPI
import org.kethereum.hashes.sha256

fun ExtendedKey.createSignature(contents: ByteArray): ByteArray {
    return CryptoAPI.signer.sign(contents.sha256().sha256(), this.keyPair.privateKey.key, true).encodeToDER()
}