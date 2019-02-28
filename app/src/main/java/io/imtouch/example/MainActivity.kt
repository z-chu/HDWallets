package io.imtouch.example

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.zchu.bridge.TextWatcherBridge
import com.github.zchu.bridge._addTextChangedListener
import com.github.zchu.common.help.showToastShort
import com.github.zchu.common.util.DebounceOnClickLister
import com.github.zchu.common.util.bindOnClickLister
import kotlinx.android.synthetic.main.activity_main.*
import org.kethereum.bip32.model.Seed
import org.kethereum.bip32.toExtendedKey
import org.kethereum.bip32.toKey
import org.kethereum.bip39.generateMnemonic
import org.kethereum.bip39.model.MnemonicWords
import org.kethereum.bip39.toSeed
import org.kethereum.bip39.validate
import org.kethereum.bip39.wordlists.WORDLIST_ENGLISH
import org.kethereum.crypto.signMessage
import org.kethereum.crypto.toECKeyPair
import org.kethereum.functions.encodeRLP
import org.kethereum.functions.toRLPList
import org.kethereum.model.ECKeyPair
import org.kethereum.model.PrivateKey
import org.kethereum.model.Transaction
import org.walleth.khex.toNoPrefixHexString

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var seed: Seed? = null

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
                if (!text.isNullOrBlank() && MnemonicWords(et_mnemonic.text.toString()).validate(WORDLIST_ENGLISH)) {
                    seed = MnemonicWords(text.toString()).toSeed()
                    et_seed.setText(seed!!.seed.toNoPrefixHexString())
                } else {
                    showToastShort("错误的助记词")
                }
            }
        }
        et_passphrase._addTextChangedListener {
            _onTextChanged { text, start, before, count ->
                if (MnemonicWords(et_mnemonic.text.toString()).validate(WORDLIST_ENGLISH)) {
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
                    tv_derivation_path.text=path
                    it.toKey(path).let {
                        tv_extended_private_key.setText(it.serialize())
                        tv_extended_public_key.setText(it.serialize(true))
                    }
                }

            }

        }


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_mnemonic_en -> {
                et_mnemonic.setText(generateMnemonic(wordList = WORDLIST_ENGLISH))

            }
/*            R.id.tv_mnemonic_ja -> {
                et_mnemonic.setText( generateMnemonic(wordList = WORDLIST_JAPANESE))
            }
            R.id.tv_mnemonic_es -> {
                et_mnemonic.setText( generateMnemonic(wordList = WORDLIST_SPANISH))
            }
            R.id.tv_mnemonic_Hans -> {
                et_mnemonic.setText( generateMnemonic(wordList = WORDLIST_CHINESE_SIMPLIFIED))
            }
            R.id.tv_mnemonic_Hant -> {
                et_mnemonic.setText( generateMnemonic(wordList = WORDLIST_CHINESE_TRADITIONAL))
            }
            R.id.tv_mnemonic_fr -> {
                et_mnemonic.setText( generateMnemonic(wordList = WORDLIST_FRENCH))
            }
            R.id.tv_mnemonic_it -> {
                et_mnemonic.setText( generateMnemonic(wordList = WORDLIST_ITALIAN))
            }
            R.id.tv_mnemonic_ko -> {
                et_mnemonic.setText( generateMnemonic(wordList = WORDLIST_KOREAN))
            }*/
        }

    }
}
