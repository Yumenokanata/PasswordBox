package indi.yume.view.appkotlin.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import indi.yume.view.appkotlin.R
import indi.yume.view.appkotlin.database.DataTable
import indi.yume.view.appkotlin.util.bindView

class EditActivity : AppCompatActivity() {

    val editTitleEditText    : EditText by bindView(R.id.edit_title_edit_text)
    val editWebsiteEditText  : EditText by bindView(R.id.edit_website_edit_text)
    val editUserEditText     : EditText by bindView(R.id.edit_user_edit_text)
    val editPasswordTextView : TextView by bindView(R.id.edit_password_text_view)
    val editNoteEditText     : EditText by bindView(R.id.edit_note_edit_text)
    val saveButton           : Button   by bindView(R.id.save_button)

    private var dataId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val dataTable = dataFromIntent
        if (dataTable != null) {
            dataId = dataTable.id
            initDataView(dataTable)
        }

        initListener()
    }

    private val dataFromIntent: DataTable?
        get() {
            val intent = intent ?: return null

            if (!intent.hasExtra(INTENT_KEY_ID))
                return null
            val id = intent.getLongExtra(INTENT_KEY_ID, -1)

            return DataTable.getDataById(id)
        }

    private fun initDataView(dataTable: DataTable) {
        editTitleEditText.setText(dataTable.title)
        editWebsiteEditText.setText(dataTable.website)
        editUserEditText.setText(dataTable.username)
        editPasswordTextView.text = dataTable.password
        editNoteEditText.setText(dataTable.note)
    }

    private fun initListener() {
        editPasswordTextView.setOnClickListener {
            startActivityForResult(
                    EditPwdActivity.createIntent(
                            this@EditActivity,
                            editPasswordTextView.text.toString()),
                    1)
        }

        saveButton.setOnClickListener {
            val dataTable = dataFromView
            if (dataId != null) {
                dataTable.id = dataId
                dataTable.update()
            } else {
                dataTable.save()
            }
            setResultData(dataTable)
            finish()
        }
    }

    private fun setResultData(dataTable: DataTable) {
        val intent = intent.putExtra(RESULT_DATA_USER_DATA, dataTable)
        setResult(RESULT_OK, intent)
    }

    private val dataFromView: DataTable
        get() {
            val dataTable = DataTable()
            dataTable.title = editTitleEditText.text.toString()
            dataTable.website = editWebsiteEditText.text.toString()
            dataTable.username = editUserEditText.text.toString()
            dataTable.password = editPasswordTextView.text.toString()
            dataTable.note = editNoteEditText.text.toString()

            return dataTable
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        val pwd = EditPwdActivity.getDataFromResult(resultCode, data.extras)
        editPasswordTextView.text = pwd
    }

    companion object {
        private val INTENT_KEY_ID = "table_id"

        private val RESULT_DATA_USER_DATA = "user_data"

        fun createIntent(context: Context, id: Long?): Intent {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra(INTENT_KEY_ID, id)
            return intent
        }

        fun getDataFromResult(resultCode: Int, data: Bundle?): DataTable? {
            if (resultCode == RESULT_OK && data != null)
                return data.getSerializable(RESULT_DATA_USER_DATA) as DataTable?
            return null
        }
    }
}
