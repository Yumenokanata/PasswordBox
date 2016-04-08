package indi.yume.view.appkotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView

import indi.yume.tools.adapter_renderer.RendererAdapter
import indi.yume.view.appkotlin.R
import indi.yume.view.appkotlin.database.DataTable
import indi.yume.view.appkotlin.renderer.ItemRenderer
import indi.yume.view.appkotlin.util.bindView

class MainActivity : AppCompatActivity() {

    val mainListView: ListView by bindView(R.id.main_list_view)
    val addItemButton: Button by bindView(R.id.add_item_button)

    lateinit var adapter: RendererAdapter<DataTable>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = RendererAdapter<DataTable>(DataTable.allData, this, ItemRenderer::class.java)
        mainListView.adapter = adapter
        mainListView.setOnItemClickListener { adapterView, view, i, l ->
            val id = (adapter.getItem(i) as DataTable).id
            startActivityForResult(ShowActivity.createIntent(this@MainActivity, id), 1)
        }

        addItemButton.setOnClickListener { startActivityForResult(EditActivity.createIntent(this@MainActivity, null), 1) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        adapter.setContentList(DataTable.allData)
        adapter.notifyDataSetChanged()
    }
}
