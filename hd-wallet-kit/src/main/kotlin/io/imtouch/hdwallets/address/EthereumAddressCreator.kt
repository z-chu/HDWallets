package io.imtouch.hdwallets.address

import org.kethereum.crypto.publicKeyFromPrivate
import org.kethereum.crypto.toAddress
import org.kethereum.model.ECKeyPair
import org.kethereum.model.PrivateKey
import org.kethereum.model.PublicKey

@Suppress("MemberVisibilityCanBePrivate", "unused")
class EthereumAddressCreator {


    fun fromPublicKey(publicKey: PublicKey): String {
        return publicKey.toAddress().hex
    }

    fun fromPrivateKey(privateKey: PrivateKey): String {
        val publicKey = publicKeyFromPrivate(privateKey)
        return fromPublicKey(publicKey)
    }

    fun fromECKeyPair(key: ECKeyPair): String {
        return fromPublicKey(key.publicKey)
    }
}