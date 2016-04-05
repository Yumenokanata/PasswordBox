package indi.yume.app.passwordbox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import indi.yume.app.passwordbox.R;
import indi.yume.app.passwordbox.util.GeneratePwd;
import indi.yume.app.passwordbox.view.PwdCheckView;

public class EditPwdActivity extends AppCompatActivity {
    private static final String INTENT_KEY_PWD = "password";

    private static final String RESULT_DATA_PWD = "result_pwd";

    private static final int MIN_PWD_LENGTH = 8;

    @Bind(R.id.password_edit_text)
    EditText passwordEditText;
    @Bind(R.id.small_a_to_z_check_box)
    CheckBox smallAToZCheckBox;
    @Bind(R.id.bigger_a_to_z_check_box)
    CheckBox biggerAToZCheckBox;
    @Bind(R.id.number_check_box)
    CheckBox numberCheckBox;
    @Bind(R.id.special_char_check_box)
    CheckBox specialCharCheckBox;
    @Bind(R.id.pwd_length_text_view)
    TextView pwdLengthTextView;
    @Bind(R.id.pwd_length_seek_bar)
    SeekBar pwdLengthSeekBar;
    @Bind(R.id.generate_button)
    Button generateButton;
    @Bind(R.id.check_pwd_view)
    PwdCheckView checkPwdView;
    @Bind(R.id.save_pwd_button)
    Button savePwdButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pwd);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String pwd = intent != null ? intent.getStringExtra(INTENT_KEY_PWD) : "";

        setPwd(pwd);

        pwdLengthTextView.setText(String.valueOf(pwdLengthSeekBar.getProgress() + MIN_PWD_LENGTH));
        initListener();
    }

    private void initListener() {
        pwdLengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                pwdLengthTextView.setText(String.valueOf(i + MIN_PWD_LENGTH));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkPwdView.checkLevel(editable.toString());
            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd = GeneratePwd.getPwd(
                        pwdLengthSeekBar.getProgress() + MIN_PWD_LENGTH,
                        biggerAToZCheckBox.isChecked(),
                        smallAToZCheckBox.isChecked(),
                        numberCheckBox.isChecked(),
                        specialCharCheckBox.isChecked());
                setPwd(pwd);
            }
        });

        savePwdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra(RESULT_DATA_PWD, passwordEditText.getText().toString());
                setResult(1, intent);
                finish();
            }
        });
    }

    private void setPwd(String pwd) {
        passwordEditText.setText(pwd);
        checkPwdView.checkLevel(pwd);
    }

    @OnClick({R.id.small_a_to_z_check_box,
            R.id.bigger_a_to_z_check_box,
            R.id.number_check_box,
            R.id.special_char_check_box})
    public void onClickCheckBox(View view) {
        int checkNum = 0;
        if (smallAToZCheckBox.isChecked()) checkNum++;
        if (biggerAToZCheckBox.isChecked()) checkNum++;
        if (numberCheckBox.isChecked()) checkNum++;
        if (specialCharCheckBox.isChecked()) checkNum++;

        if (checkNum == 0)
            numberCheckBox.setChecked(true);
    }

    public static Intent createIntent(Context context, String pwd) {
        Intent intent = new Intent(context, EditPwdActivity.class);
        intent.putExtra(INTENT_KEY_PWD, pwd);
        return intent;
    }

    @Nullable
    public static String getDataFromResult(int resultCode, Bundle data) {
        if(resultCode == 1 && data != null)
            return data.getString(RESULT_DATA_PWD);
        return null;
    }
}
