package io.imtouch.hdwallets.validators

import org.bitcoinj.core.Address
import org.bitcoinj.core.AddressFormatException
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.params.*

/**
 * Created by xyz on 2018/3/12.
 */

object BitcoinAddressValidator {


    @JvmStatic
    fun isValidAddress(addressBase58: String, networkParameters: NetworkParameters): Boolean {
        return try {
            Address.fromBase58(networkParameters, addressBase58)
            true
        } catch (ignore: AddressFormatException) {
            false
        }

    }

    /**
     * 验证地址是否合法，任意一个 NetworkParameters 通过就算合法
     */
    @JvmStatic
    fun isValidAddress(addressBase58: String): Boolean {
        return getNetParamsByAddress(addressBase58) != null
    }


    /**
     * 通过 Address 找到所对应的 NetworkParameters
     */
    @JvmStatic
    fun getNetParamsByAddress(addressBase58: String): NetworkParameters? {
        val netParamsList = arrayListOf(
            MainNetParams.get(),
            TestNet3Params.get(),
            UnitTestParams.get(),
            RegTestParams.get(),
            TestNet2Params.get()
        )
        for (netParams in netParamsList) {
            if (isValidAddress(addressBase58, netParams)) {
                return netParams
            }
        }
        return null
    }


}
