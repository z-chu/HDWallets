package io.imtouch.hdwallets

import org.bitcoinj.core.ECKey
import org.bitcoinj.core.NetworkParameters
import org.kethereum.model.PrivateKey

fun PrivateKey.toBitCoinWiF(params: NetworkParameters): String {
    return ECKey.fromPrivate(key).getPrivateKeyAsWiF(params)
}