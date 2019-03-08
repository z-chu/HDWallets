package io.imtouch.hdwallets.validators

import org.bitcoinj.core.Address
import org.bitcoinj.core.AddressFormatException
import org.bitcoinj.core.NetworkParameters

/**
 * Created by xyz on 2018/3/12.
 */

object BitcoinAddressValidator {


    @JvmStatic
    fun isValidAddress(networkParameters: NetworkParameters, addressBase58: String): Boolean {
        return try {
            Address.fromBase58(networkParameters, addressBase58)
            true
        } catch (e: AddressFormatException) {
            e.printStackTrace()
            false
        }
    }


}
