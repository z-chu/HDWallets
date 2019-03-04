package io.imtouch.hdwallets

import org.kethereum.bip32.model.ExtendedKey
import org.kethereum.crypto.getCompressedPublicKey
import org.kethereum.hashes.ripemd160
import org.kethereum.hashes.sha256

class HDPublicKey() {

    var index = 0
    var external = true

    var publicKey: ByteArray = byteArrayOf()
    var publicKeyHash: ByteArray = byteArrayOf()


    constructor(index: Int, external: Boolean, key: ExtendedKey) : this() {
        this.index = index
        this.external = external
        this.publicKey = key.keyPair.getCompressedPublicKey()
        this.publicKeyHash = publicKey.sha256().ripemd160()
    }

}
