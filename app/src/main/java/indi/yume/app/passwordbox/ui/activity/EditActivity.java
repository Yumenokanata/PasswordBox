package indi.yume.app.passwordbox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import indi.yume.app.passwordbox.R;
import indi.yume.app.passwordbox.model.database.DataTable;

public class EditActivity extends AppCompatActivity {
    private static final String INTENT_KEY_ID = "table_id";

    private static final String RESULT_DATA_USER_DATA = "user_data";

    @Bind(R.id.edit_title_edit_text)
    EditText editTitleEditText;
    @Bind(R.id.edit_website_edit_text)
    EditText editWebsiteEditText;
    @Bind(R.id.edit_user_edit_text)
    EditText editUserEditText;
    @Bind(R.id.edit_password_text_view)
    TextView editPasswordTextView;
    @Bind(R.id.edit_note_edit_text)
    EditText editNoteEditText;
    @Bind(R.id.save_button)
    Button saveButton;

    private Long dataId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);

        DataTable dataTable = getDataFromIntent();
        if(dataTable != null) {
            dataId = dataTable.getId();
            initDataView(dataTable);
        }

        initListener();
    }

    @Nullable
    private DataTable getDataFromIntent() {
        Intent intent = getIntent();
        if (intent == null)
            return null;

        if (!intent.hasExtra(INTENT_KEY_ID))
            return null;
        long id = intent.getLongExtra(INTENT_KEY_ID, -1);

        return DataTable.getDataById(id);
    }

    private void initDataView(DataTable dataTable) {
        editTitleEditText    .setText(dataTable.getTitle());
        editWebsiteEditText  .setText(dataTable.getWebsite());
        editUserEditText     .setText(dataTable.getUsername());
        editPasswordTextView .setText(dataTable.getPassword());
        editNoteEditText     .setText(dataTable.getNote());
    }

    private void initListener() {
        editPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        EditPwdActivity.createIntent(
                                EditActivity.this,
                                editPasswordTextView.getText().toString()),
                        1);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataTable dataTable = getDataFromView();
                if(dataId != null) {
                    dataTable.setId(dataId);
                    dataTable.update();
                } else {
                    dataTable.save();
                }
                setResultData(dataTable);
                finish();
            }
        });
    }

    private void setResultData(DataTable dataTable) {
        Intent intent = getIntent().putExtra(RESULT_DATA_USER_DATA, dataTable);
        setResult(RESULT_OK, intent);
    }

    private DataTable getDataFromView() {
        DataTable dataTable = new DataTable();
        dataTable   .setTitle(editTitleEditText.getText().toString());
        dataTable .setWebsite(editWebsiteEditText.getText().toString());
        dataTable.setUsername(editUserEditText.getText().toString());
        dataTable.setPassword(editPasswordTextView.getText().toString());
        dataTable    .setNote(editNoteEditText.getText().toString());

        return dataTable;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String pwd = EditPwdActivity.getDataFromResult(resultCode, data.getExtras());
        editPasswordTextView.setText(pwd);
    }

    public static Intent createIntent(Context context, Long id) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra(INTENT_KEY_ID, id);
        return intent;
    }

    @Nullable
    public static DataTable getDataFromResult(int resultCode, Bundle data) {
        if(resultCode == RESULT_OK && data != null)
            return (DataTable) data.getSerializable(RESULT_DATA_USER_DATA);
        return null;
    }
}
