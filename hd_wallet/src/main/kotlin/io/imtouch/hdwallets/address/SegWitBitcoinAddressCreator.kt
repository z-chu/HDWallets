package io.imtouch.hdwallets.address


import io.imtouch.hdwallets.compress
import org.bitcoinj.core.*
import org.bitcoinj.params.MainNetParams
import org.kethereum.encodings.decodeBase58
import org.kethereum.hashes.ripemd160
import org.kethereum.hashes.sha256
import org.kethereum.model.ECKeyPair
import org.walleth.khex.hexToByteArray
import org.walleth.khex.toHexString

class SegWitBitcoinAddressCreator(private val networkParameters: NetworkParameters) {

    fun fromPrivateKey(prvKeyHex: String): String {
        val key: ECKey
        if (prvKeyHex.length == 51 || prvKeyHex.length == 52) {
            val dumpedPrivateKey = DumpedPrivateKey.fromBase58(networkParameters, prvKeyHex)
            key = dumpedPrivateKey.key
            if (!key.isCompressed()) {
                throw IllegalStateException("segwit_needs_compress_public_key")
            }
        } else {
            key = ECKey.fromPrivate(prvKeyHex.hexToByteArray(), true)
        }
        return calcSegWitAddress(key.pubKeyHash)
    }

    fun fromPrivateKey(prvKeyBytes: ByteArray): String {
        val key = ECKey.fromPrivate(prvKeyBytes, true)
        key.getPrivateKeyAsWiF(MainNetParams.get()).decodeBase58()
        return calcSegWitAddress(key.pubKeyHash)
    }

    private fun calcSegWitAddress(pubKeyHash: ByteArray): String {
        val redeemScript = pubKeyHash.toHexString("0x0014")
        return Address.fromP2SHHash(networkParameters, Utils.sha256hash160(redeemScript.hexToByteArray()))
            .toBase58()
    }

    fun fromPrivateKey(ecKey: ECKey): Address {
        val redeemScript =  ecKey.pubKeyHash.toHexString("0x0014")
        return Address.fromP2SHHash(networkParameters, Utils.sha256hash160(redeemScript.hexToByteArray()))
    }

    fun fromECKeyPair(key: ECKeyPair): String {
        val redeemScript = key.publicKey.compress().sha256().ripemd160().toHexString("0x0014")

        return  Address.fromP2SHHash(networkParameters,redeemScript.hexToByteArray().sha256().ripemd160()).toBase58()
    }
}
