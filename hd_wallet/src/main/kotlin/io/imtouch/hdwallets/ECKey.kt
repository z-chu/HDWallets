package io.imtouch.hdwallets

import org.bitcoinj.core.ECKey
import org.kethereum.crypto.toECKeyPair
import org.kethereum.model.ECKeyPair
import org.kethereum.model.PrivateKey
import org.walleth.khex.toHexString


fun ECKey.toECKeyPair(): ECKeyPair {
    return PrivateKey(privKey).toECKeyPair()
}

fun ECKey.getEthereumPublicKey(): ByteArray {
    val normalize = pubKeyPoint.normalize()
    var XEncoded = normalize.affineXCoord.encoded
    var YEncoded = normalize.affineYCoord.encoded
    XEncoded = padTo32(XEncoded)
    YEncoded = padTo32(YEncoded)
    val concat = ByteArray(XEncoded.size + YEncoded.size)
    System.arraycopy(
        XEncoded, 0, concat, 0,
        Math.min(XEncoded.size, concat.size)
    )
    System.arraycopy(
        YEncoded, 0, concat, XEncoded.size,
        Math.min(XEncoded.size, concat.size)
    )
    return concat
}

fun ECKey.getEthereumPublicKeyAsHex(): String {
    return getEthereumPublicKey().toHexString()
}


private fun padTo32(contents: ByteArray): ByteArray {
    var input = contents
    while (input.size < 32) {
        input = insertByte(input, 0.toByte())
    }
    return input
}

private fun insertByte(bytes: ByteArray, b: Byte): ByteArray {
    val result = copyOfToEnd(bytes, bytes.size + 1)
    result[0] = b
    return result
}

private fun copyOfToEnd(original: ByteArray, newLength: Int): ByteArray {
    val copy = ByteArray(newLength)
    System.arraycopy(
        original, 0, copy, Math.max(0, newLength - original.size),
        Math.min(original.size, newLength)
    )
    return copy
}