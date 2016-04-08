package indi.yume.view.appkotlin.renderer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import indi.yume.tools.adapter_renderer.BaseRenderer
import indi.yume.view.appkotlin.R
import indi.yume.view.appkotlin.database.DataTable
import indi.yume.view.appkotlin.util.bindView
import indi.yume.view.appkotlin.view.CircleView
import org.jetbrains.anko.find
import kotlin.properties.Delegates

/**
 * Created by bush2 on 2016/4/4.
 */
class ItemRenderer : BaseRenderer<DataTable>() {
    var mainView: View by Delegates.notNull()

    val titleTextView: TextView   by lazy { mainView.find<TextView>(R.id.head_text_view) }
    val websiteTextView: TextView by lazy { mainView.find<TextView>(R.id.website_text_view) }
    val headTextView: CircleView  by lazy { mainView.find<CircleView>(R.id.head_text_view) }

    override fun render() {
        val dataTable = content

        headTextView.setText(dataTable.title)
        headTextView.setColor(dataTable.labelColor)
        titleTextView.setText(dataTable.title)
        websiteTextView.setText(dataTable.website)
    }

    override fun inflate(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View {
        return layoutInflater.inflate(R.layout.item_layout, viewGroup, false)
    }

    override fun findView(view: View) {
        mainView = view
    }

    override fun setListener(view: View) {

    }
}
