package io.imtouch.hdwallets

import org.kethereum.crypto.CURVE
import org.kethereum.extensions.toBytesPadded
import org.kethereum.model.PUBLIC_KEY_SIZE
import org.kethereum.model.PublicKey

fun PublicKey.compress(): ByteArray {
    //add the uncompressed prefix
    val ret = key.toBytesPadded(PUBLIC_KEY_SIZE + 1)
    ret[0] = 4
    val point = CURVE.decodePoint(ret)
    return point.encoded(true)
}