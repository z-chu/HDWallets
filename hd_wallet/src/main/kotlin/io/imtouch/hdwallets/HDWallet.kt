package io.imtouch.hdwallets

import org.kethereum.bip32.generateChildKey
import org.kethereum.bip32.model.ExtendedKey
import org.kethereum.bip32.model.Seed
import org.kethereum.bip32.toExtendedKey
import org.kethereum.bip39.model.MnemonicWords
import org.kethereum.bip39.seed.toSeed
import org.kethereum.bip44.BIP44

class HDWallet(val rootKey: ExtendedKey, val coinType: Int, val gapLimit: Int = 20) {

    constructor(mnemonicWords: MnemonicWords, coinType: Int, gapLimit: Int = 20) : this(
        mnemonicWords.toSeed(),
        coinType,
        gapLimit
    )

    constructor(seed: Seed, coinType: Int, gapLimit: Int = 20) : this(seed.toExtendedKey(), coinType, gapLimit)


    enum class Chain {
        EXTERNAL, INTERNAL
    }

    // m / purpose' / coin_type' / account' / change / address_index
    //
    // Purpose is a constant set to 44' (or 0x8000002C) following the BIP43 recommendation.
    // It indicates that the subtree of this node is used according to this specification.
    // Hardened derivation is used at this level.
    private var purpose: Int = 44

    // One master node (seed) can be used for unlimited number of independent cryptocoins such as Bitcoin, Litecoin or Namecoin. However, sharing the same space for various cryptocoins has some disadvantages.
    // This level creates a separate subtree for every cryptocoin, avoiding reusing addresses across cryptocoins and improving privacy issues.
    // Coin type is a constant, set for each cryptocoin. Cryptocoin developers may ask for registering unused number for their project.
    // The list of already allocated coin types is in the chapter "Registered coin types" below.
    // Hardened derivation is used at this level.
    // network.name == MainNet().name ? 0 : 1
    // private var coinType: Int = 0

    fun hdPublicKey(account: Int, index: Int, external: Boolean): HDPublicKey {
        return HDPublicKey(
            index = index,
            external = external,
            key = generateKey(account = account, index = index, chain = if (external) 0 else 1)
        )
    }

    fun receiveHDPublicKey(account: Int, index: Int): HDPublicKey {
        return HDPublicKey(
            index = index,
            external = true,
            key = generateKey(account = account, index = index, chain = 0)
        )
    }

    fun changeHDPublicKey(account: Int, index: Int): HDPublicKey {
        return HDPublicKey(
            index = index,
            external = false,
            key = generateKey(account = account, index = index, chain = 1)
        )
    }

    fun generateKey(account: Int, index: Int, external: Boolean): ExtendedKey {
        return generateKey(account, index, if (external) Chain.EXTERNAL.ordinal else Chain.INTERNAL.ordinal)
    }

    fun generateKey(account: Int, index: Int, chain: Int): ExtendedKey {
        return generateKey("m/$purpose'/$coinType'/$account'/$chain/$index")
    }


    private fun generateKey(path: String): ExtendedKey {
        return BIP44(path).path
            .fold(rootKey) { current, bip44Element ->
                current.generateChildKey(bip44Element)
            }
    }

}
