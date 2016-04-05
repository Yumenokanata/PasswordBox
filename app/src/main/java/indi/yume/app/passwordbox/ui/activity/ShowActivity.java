package indi.yume.app.passwordbox.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import indi.yume.app.passwordbox.R;
import indi.yume.app.passwordbox.model.database.DataTable;

public class ShowActivity extends AppCompatActivity {
    private static final String INTENT_KEY_ID = "table_id";

    @Bind(R.id.show_title_text_view)
    TextView showTitleTextView;
    @Bind(R.id.show_website_text_view)
    TextView showWebsiteTextView;
    @Bind(R.id.show_user_text_view)
    TextView showUserTextView;
    @Bind(R.id.show_password_text_view)
    TextView showPasswordTextView;
    @Bind(R.id.show_note_text_view)
    TextView showNoteTextView;
    @Bind(R.id.edit_button)
    Button editButton;
    @Bind(R.id.delete_button)
    Button deleteButton;
    @Bind(R.id.base_main_layout)
    LinearLayout baseMainLayout;

    private DataTable dataTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null)
            return;

        if (!intent.hasExtra(INTENT_KEY_ID))
            return;
        long id = intent.getLongExtra(INTENT_KEY_ID, -1);

        dataTable = DataTable.getDataById(id);

        if (dataTable == null)
            return;

        initDataView(dataTable);
        initListener();
    }

    private void initDataView(DataTable dataTable) {
        if (dataTable == null)
            return;

        showTitleTextView.setText(dataTable.getTitle());
        showWebsiteTextView.setText(dataTable.getWebsite());
        showUserTextView.setText(dataTable.getUsername());
        showPasswordTextView.setText(dataTable.getPassword());
        showNoteTextView.setText(dataTable.getNote());
    }

    private void initListener() {
        editButton.setOnClickListener(view -> startActivityForResult(
                EditActivity.createIntent(
                        ShowActivity.this,
                        dataTable != null ? dataTable.getId() : null),
                1));
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataTable != null) {
                    dataTable.delete();
                }
                finish();
            }
        });

        showTitleTextView.setOnClickListener(v -> copyToClipboard("title", showTitleTextView.getText().toString()));
        showWebsiteTextView.setOnClickListener(v -> copyToClipboard("website", showWebsiteTextView.getText().toString()));
        showUserTextView.setOnClickListener(v -> copyToClipboard("username", showUserTextView.getText().toString()));
        showPasswordTextView.setOnClickListener(v -> copyToClipboard("password", showPasswordTextView.getText().toString()));
        showNoteTextView.setOnClickListener(v -> copyToClipboard("note", showNoteTextView.getText().toString()));
    }

    private void copyToClipboard(String label, String data) {
        ClipboardManager clipboardManager = ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE));
        ClipData clipData = ClipData.newPlainText(label, data);
        clipboardManager.setPrimaryClip(clipData);

        Snackbar.make(baseMainLayout, label + "已复制到剪切板", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dataTable = EditActivity.getDataFromResult(resultCode, data.getExtras());
        if (dataTable == null)
            return;

        initDataView(dataTable);
    }

    public static Intent createIntent(Context context, @Nullable Long id) {
        Intent intent = new Intent(context, ShowActivity.class);
        if (id != null)
            intent.putExtra(INTENT_KEY_ID, id);
        return intent;
    }
}
