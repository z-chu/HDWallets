package io.imtouch.hdwallets

import org.kethereum.crypto.api.ec.ECDSASignature
import org.spongycastle.asn1.ASN1Integer
import org.spongycastle.asn1.DERSequenceGenerator
import java.io.ByteArrayOutputStream
import java.io.IOException

fun ECDSASignature.encodeToDER(): ByteArray {

    try {
        return ByteArrayOutputStream(80).use { outStream ->
            val seq = DERSequenceGenerator(outStream)
            seq.addObject(ASN1Integer(r))
            seq.addObject(ASN1Integer(s))
            seq.close()
            outStream.toByteArray()
        }
    } catch (exc: IOException) {
        throw IllegalStateException("Unexpected IOException", exc)
    }
}