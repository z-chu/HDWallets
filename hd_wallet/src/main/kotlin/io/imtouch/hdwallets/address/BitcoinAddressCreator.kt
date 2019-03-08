package io.imtouch.hdwallets.address

import io.imtouch.hdwallets.compress
import org.bitcoinj.core.Address
import org.bitcoinj.core.NetworkParameters
import org.kethereum.crypto.publicKeyFromPrivate
import org.kethereum.hashes.ripemd160
import org.kethereum.hashes.sha256
import org.kethereum.model.ECKeyPair
import org.kethereum.model.PrivateKey
import org.kethereum.model.PublicKey

@Suppress("MemberVisibilityCanBePrivate", "unused")
class BitcoinAddressCreator(private val networkParameters: NetworkParameters) {

    fun fromPublicKey(publicKey: PublicKey): String {
        return Address(networkParameters, publicKey.compress().sha256().ripemd160()).toBase58()
    }

    fun fromPrivateKey(privateKey: PrivateKey): String {
        return fromPublicKey(publicKeyFromPrivate(privateKey))
    }

    fun fromECKeyPair(key: ECKeyPair): String {
        return fromPublicKey(key.publicKey)
    }
}