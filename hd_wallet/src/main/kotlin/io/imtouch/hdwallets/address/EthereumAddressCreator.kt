package io.imtouch.hdwallets.address

import io.imtouch.hdwallets.toECKeyPair
import org.bitcoinj.core.ECKey
import org.kethereum.bip32.model.XPriv
import org.kethereum.crypto.publicKeyFromPrivate
import org.kethereum.crypto.toAddress
import org.kethereum.model.ECKeyPair
import org.kethereum.model.PrivateKey
import org.kethereum.model.PublicKey

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

    fun fromECKey(key: ECKey): String {
        return fromECKeyPair(key.toECKeyPair())
    }

}