package io.imtouch.hdwallets

import org.kethereum.bip32.generateChildKey
import org.kethereum.bip32.model.ExtendedKey
import org.kethereum.bip44.BIP44
import org.kethereum.crypto.CryptoAPI
import org.kethereum.hashes.sha256

fun ExtendedKey.createSignature(contents: ByteArray): ByteArray {
    return CryptoAPI.signer.sign(contents.sha256().sha256(), this.keyPair.privateKey.key, true).encodeToDER()
}


fun ExtendedKey.generateChildKey(pathString: String): ExtendedKey {
    return generateChildKey(BIP44(pathString))
}

fun ExtendedKey.generateChildKey(biP44: BIP44): ExtendedKey {
    return biP44.path
        .fold(this) { current, bip44Element ->
            current.generateChildKey(bip44Element)
        }
}