package io.imtouch.hdwallets.validators

import com.google.common.base.Preconditions.checkState
import com.google.common.base.Strings
import org.kethereum.keccakshortcut.keccak
import org.walleth.khex.clean0xPrefix
import org.walleth.khex.toHexString
import org.walleth.khex.toNoPrefixHexString
import java.util.regex.Pattern

/**
 * Created by xyz on 2018/3/12.
 */

object ETHAddressValidator {

    private val ignoreCaseAddrPattern = Pattern.compile(
        "^(0x)?[0-9a-f]{40}$",
        Pattern.CASE_INSENSITIVE
    )
    private val lowerCaseAddrPattern = Pattern.compile("^(0x)?[0-9a-f]{40}$")
    private val upperCaseAddrPattern = Pattern.compile("^(0x)?[0-9A-F]{40}$")


    @JvmStatic
    fun isValidAddress(address: String): Boolean {

        // if not [0-9]{40} return false
        return if (Strings.isNullOrEmpty(address) || !ignoreCaseAddrPattern.matcher(address).find()) {
            false
        } else if (lowerCaseAddrPattern.matcher(address).find() || upperCaseAddrPattern.matcher(address).find()) {
            // if it's all small caps or caps return true
            true
        } else {
            // if it is mixed caps it is a checksum address and needs to be validated
            validateChecksumAddress(address)
        }
    }

    private fun validateChecksumAddress(addressString: String): Boolean {
        val address = addressString.clean0xPrefix()
        checkState(address.length == 40)
        val hash = address.toLowerCase().toByteArray().keccak().toNoPrefixHexString()

        for (i in 0..39) {
            if (Character.isLetter(address[i])) {
                // each uppercase letter should correlate with a first bit of 1 in the hash
                // char with the same index, and each lowercase letter with a 0 bit
                val charInt = Integer.parseInt(Character.toString(hash.get(i)), 16)
                if (Character.isUpperCase(address[i]) && charInt <= 7 || Character.isLowerCase(address[i]) && charInt > 7) {
                    return false
                }
            }
        }
        return true
    }

}
