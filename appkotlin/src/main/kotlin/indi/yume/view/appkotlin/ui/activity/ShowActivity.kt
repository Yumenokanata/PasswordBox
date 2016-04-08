package indi.yume.view.appkotlin.ui.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

import indi.yume.view.appkotlin.R
import indi.yume.view.appkotlin.database.DataTable
import indi.yume.view.appkotlin.util.bindView

class ShowActivity : AppCompatActivity() {

    val showTitleTextView   : TextView     by bindView(R.id.show_title_text_view)
    val showWebsiteTextView : TextView     by bindView(R.id.show_website_text_view)
    val showUserTextView    : TextView     by bindView(R.id.show_user_text_view)
    val showPasswordTextView: TextView     by bindView(R.id.show_password_text_view)
    val showNoteTextView    : TextView     by bindView(R.id.show_note_text_view)
    val editButton          : Button       by bindView(R.id.edit_button)
    val deleteButton        : Button       by bindView(R.id.delete_button)
    val baseMainLayout      : LinearLayout by bindView(R.id.base_main_layout)

    private var dataTable: DataTable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        val intent = intent ?: return

        if (!intent.hasExtra(INTENT_KEY_ID))
            return
        val id = intent.getLongExtra(INTENT_KEY_ID, -1)

        dataTable = DataTable.getDataById(id)

        if (dataTable == null)
            return

        initDataView(dataTable)
        initListener()
    }

    private fun initDataView(dataTable: DataTable?) {
        if (dataTable == null)
            return

        showTitleTextView.setText(dataTable.title)
        showWebsiteTextView.setText(dataTable.website)
        showUserTextView.setText(dataTable.username)
        showPasswordTextView.setText(dataTable.password)
        showNoteTextView.setText(dataTable.note)
    }

    private fun initListener() {
        editButton.setOnClickListener { view ->
            startActivityForResult(
                    EditActivity.createIntent(
                            this@ShowActivity,
                            dataTable?.id?.toLong()),
                    1)
        }
        deleteButton.setOnClickListener {
            if (dataTable != null) {
                dataTable!!.delete()
            }
            finish()
        }

        showTitleTextView.setOnClickListener { v -> copyToClipboard("title", showTitleTextView.text.toString()) }
        showWebsiteTextView.setOnClickListener { v -> copyToClipboard("website", showWebsiteTextView.text.toString()) }
        showUserTextView.setOnClickListener { v -> copyToClipboard("username", showUserTextView.text.toString()) }
        showPasswordTextView.setOnClickListener { v -> copyToClipboard("password", showPasswordTextView.text.toString()) }
        showNoteTextView.setOnClickListener { v -> copyToClipboard("note", showNoteTextView.text.toString()) }
    }

    private fun copyToClipboard(label: String, data: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(label, data)
        clipboardManager.primaryClip = clipData

        Snackbar.make(baseMainLayout, label + "已复制到剪切板", Snackbar.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        dataTable = EditActivity.getDataFromResult(resultCode, data.extras)
        if (dataTable == null)
            return

        initDataView(dataTable)
    }

    companion object {
        private val INTENT_KEY_ID = "table_id"

        fun createIntent(context: Context, id: Long?): Intent {
            val intent = Intent(context, ShowActivity::class.java)
            if (id != null)
                intent.putExtra(INTENT_KEY_ID, id)
            return intent
        }
    }
}
