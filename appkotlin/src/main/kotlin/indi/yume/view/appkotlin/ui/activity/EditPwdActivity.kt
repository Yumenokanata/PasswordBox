package indi.yume.view.appkotlin.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

import indi.yume.view.appkotlin.R
import indi.yume.view.appkotlin.util.GeneratePwd
import indi.yume.view.appkotlin.util.bindView
import indi.yume.view.appkotlin.view.PwdCheckView
import org.jetbrains.anko.onSeekBarChangeListener
import org.jetbrains.anko.textChangedListener

class EditPwdActivity : AppCompatActivity() {

    val passwordEditText    : EditText     by bindView(R.id.password_edit_text)
    val smallAToZCheckBox   : CheckBox     by bindView(R.id.small_a_to_z_check_box)
    val biggerAToZCheckBox  : CheckBox     by bindView(R.id.bigger_a_to_z_check_box)
    val numberCheckBox      : CheckBox     by bindView(R.id.number_check_box)
    val specialCharCheckBox : CheckBox     by bindView(R.id.special_char_check_box)
    val pwdLengthTextView   : TextView     by bindView(R.id.pwd_length_text_view)
    val pwdLengthSeekBar    : SeekBar      by bindView(R.id.pwd_length_seek_bar)
    val generateButton      : Button       by bindView(R.id.generate_button)
    val checkPwdView        : PwdCheckView by bindView(R.id.check_pwd_view)
    val savePwdButton       : Button       by bindView(R.id.save_pwd_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pwd)

        val intent = intent
        val pwd = if (intent != null) intent.getStringExtra(INTENT_KEY_PWD) else ""

        setPwd(pwd)

        pwdLengthTextView.text = (pwdLengthSeekBar.progress + MIN_PWD_LENGTH).toString()
        initListener()
    }

    private fun initListener() {
        pwdLengthSeekBar.onSeekBarChangeListener {
            onProgressChanged { seekBar, i, b -> pwdLengthTextView.text = (i + MIN_PWD_LENGTH).toString() }
        }

        passwordEditText.textChangedListener {
            afterTextChanged { checkPwdView.checkLevel(it.toString()) }
        }

        generateButton.setOnClickListener {
            val pwd = GeneratePwd.getPwd(
                    pwdLengthSeekBar.progress + MIN_PWD_LENGTH,
                    biggerAToZCheckBox.isChecked,
                    smallAToZCheckBox.isChecked,
                    numberCheckBox.isChecked,
                    specialCharCheckBox.isChecked)
            setPwd(pwd)
        }

        savePwdButton.setOnClickListener {
            val intent = intent
            intent.putExtra(RESULT_DATA_PWD, passwordEditText.text.toString())
            setResult(1, intent)
            finish()
        }

        smallAToZCheckBox.setOnClickListener { onClickCheckBox() }
        biggerAToZCheckBox.setOnClickListener { onClickCheckBox() }
        numberCheckBox.setOnClickListener { onClickCheckBox() }
        specialCharCheckBox.setOnClickListener { onClickCheckBox() }
    }

    private fun setPwd(pwd: String) {
        passwordEditText.setText(pwd)
        checkPwdView.checkLevel(pwd)
    }

    fun onClickCheckBox() {
        var checkNum = 0
        if (smallAToZCheckBox.isChecked) checkNum++
        if (biggerAToZCheckBox.isChecked) checkNum++
        if (numberCheckBox.isChecked) checkNum++
        if (specialCharCheckBox.isChecked) checkNum++

        if (checkNum == 0)
            numberCheckBox.isChecked = true
    }

    companion object {
        private val INTENT_KEY_PWD = "password"

        private val RESULT_DATA_PWD = "result_pwd"

        private val MIN_PWD_LENGTH = 8

        fun createIntent(context: Context, pwd: String): Intent {
            val intent = Intent(context, EditPwdActivity::class.java)
            intent.putExtra(INTENT_KEY_PWD, pwd)
            return intent
        }

        fun getDataFromResult(resultCode: Int, data: Bundle?): String? {
            if (resultCode == 1 && data != null)
                return data.getString(RESULT_DATA_PWD)
            return null
        }
    }
}
