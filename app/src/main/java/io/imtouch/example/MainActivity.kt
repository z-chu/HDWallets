package io.imtouch.example

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.zchu.bridge.TextWatcherBridge
import com.github.zchu.bridge._addTextChangedListener
import com.github.zchu.common.help.showToastShort
import com.github.zchu.common.util.DebounceOnClickLister
import com.github.zchu.common.util.bindOnClickLister

import io.imtouch.hdwallets.address.EthereumAddressCreator
import io.imtouch.hdwallets.address.SegWitBitcoinAddressCreator
import io.imtouch.hdwallets.toBitCoinWiF
import kotlinx.android.synthetic.main.activity_main.*
import org.bitcoinj.params.MainNetParams
import org.kethereum.bip32.model.Seed
import org.kethereum.bip32.toExtendedKey
import org.kethereum.bip32.toKey
import org.kethereum.bip39.generateMnemonic
import org.kethereum.bip39.model.MnemonicWords
import org.kethereum.bip39.seed.toSeed
import org.kethereum.bip39.validate
import org.kethereum.bip39.wordlists.*
import org.kethereum.crypto.getCompressedPublicKey
import org.kethereum.crypto.toAddress
import org.kethereum.extensions.toHexStringNoPrefix
import org.walleth.khex.toNoPrefixHexString

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var seed: Seed? = null

    val words_en = wordListOfEnglish()
    val words_zhs =  wordListOfChineseSimplified()
    val words_zht = wordListOfChineseTraditional()
    val words_fr = wordListOfFrench()
    val words_it = wordListOfItalian()
    val words_ja = wordListOfJapanese()
    val words_ko = wordListOfKorean()
    val words_es = wordListOfSpanish()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindOnClickLister(
            DebounceOnClickLister(this),
            R.id.tv_mnemonic_en,
            R.id.tv_mnemonic_ja,
            R.id.tv_mnemonic_es,
            R.id.tv_mnemonic_Hans,
            R.id.tv_mnemonic_Hant,
            R.id.tv_mnemonic_fr,
            R.id.tv_mnemonic_it,
            R.id.tv_mnemonic_ko

        )
        et_mnemonic._addTextChangedListener {
            _onTextChanged { text, start, before, count ->
                if (!text.isNullOrBlank() && MnemonicWords(et_mnemonic.text.toString()).validateMnemonic()) {
                    seed = MnemonicWords(text.toString()).toSeed()
                    et_seed.setText(seed!!.seed.toNoPrefixHexString())
                } else {
                    showToastShort("错误的助记词")
                }
            }
        }
        et_passphrase._addTextChangedListener {
            _onTextChanged { text, start, before, count ->
                if (MnemonicWords(et_mnemonic.text.toString()).validateMnemonic()) {
                    seed = MnemonicWords(et_mnemonic.text.toString()).toSeed(text.toString())
                    et_seed.setText(seed!!.seed.toNoPrefixHexString())
                } else {
                    showToastShort("错误的助记词")
                }
            }

        }
        et_seed._addTextChangedListener {
            _onTextChanged { text, start, before, count ->
                val seed = seed
                if (!text.isNullOrBlank() && seed != null) {
                    val toExtendedKey = seed.toExtendedKey()
                    toExtendedKey.keyPair.toAddress()
                    tv_seedToExtendedKey_prv.text = toExtendedKey.serialize()
                    tv_seedToExtendedKey_pub.text = toExtendedKey.serialize(true)
                }
            }
        }
        val textWatcherBridge = TextWatcherBridge()
        et_purpose.addTextChangedListener(textWatcherBridge)
        et_coin.addTextChangedListener(textWatcherBridge)
        et_account.addTextChangedListener(textWatcherBridge)
        et_external_internal.addTextChangedListener(textWatcherBridge)

        textWatcherBridge._onTextChanged { text, start, before, count ->
            if (!et_purpose.text.isNullOrBlank()
                && !et_coin.text.isNullOrBlank()
                && !et_account.text.isNullOrBlank()
                && !et_external_internal.text.isNullOrBlank()
            ) {
                seed?.let {
                    it.toKey("m/${et_purpose.text}'/${et_coin.text}'/${et_account.text}'").let {
                        et_account_extended_prv.setText(it.serialize())
                        et_account_extended_pub.setText(it.serialize(true))
                    }
                    val path = "m/${et_purpose.text}'/${et_coin.text}'/${et_account.text}'/${et_external_internal.text}"
                    tv_derivation_path.text = path
                    it.toKey(path).let {
                        tv_extended_private_key.setText(it.serialize())
                        tv_extended_public_key.setText(it.serialize(true))
                    }
                    deriveAddress(it)
                }

            }

        }


    }

    private fun deriveAddress(seed: Seed) {
        val path = "m/${et_purpose.text}'/${et_coin.text}'/${et_account.text}'/${et_external_internal.text}/0"
        tv_path.text = path
        val extendedKey =
            seed.toKey(path)
        tv_private_key.text = "private_key:" + extendedKey.keyPair.privateKey.key.toHexStringNoPrefix() + "\n WIF：" +
                extendedKey.keyPair.privateKey.toBitCoinWiF(MainNetParams.get())
        tv_public_key.text = "public_key:" + extendedKey.keyPair.getCompressedPublicKey().toNoPrefixHexString()
        tv_address.text = "address:" + EthereumAddressCreator().fromECKeyPair(extendedKey.keyPair) + " \n base58:" +
                SegWitBitcoinAddressCreator(MainNetParams.get()).fromECKeyPair(extendedKey.keyPair)
    }

    private fun MnemonicWords.validateMnemonic(): Boolean {
        val listOf = listOf(words_en, words_es, words_fr, words_it, words_ja, words_ko, words_zhs, words_zht)
        for (list in listOf) {
            if (validate(list)) {
                return true
            }
        }
        return false

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_mnemonic_en -> {
                et_mnemonic.setText(generateMnemonic(wordList = words_en))

            }
            R.id.tv_mnemonic_ja -> {
                et_mnemonic.setText(generateMnemonic(wordList = words_ja))
            }
            R.id.tv_mnemonic_es -> {
                et_mnemonic.setText(generateMnemonic(wordList = words_es))
            }
            R.id.tv_mnemonic_Hans -> {
                et_mnemonic.setText(generateMnemonic(wordList = words_zhs))
            }
            R.id.tv_mnemonic_Hant -> {
                et_mnemonic.setText(generateMnemonic(wordList = words_zht))
            }
            R.id.tv_mnemonic_fr -> {
                et_mnemonic.setText(generateMnemonic(wordList = words_fr))
            }
            R.id.tv_mnemonic_it -> {
                et_mnemonic.setText(generateMnemonic(wordList = words_it))
            }
            R.id.tv_mnemonic_ko -> {
                et_mnemonic.setText(generateMnemonic(wordList = words_ko))
            }
        }

    }

}
