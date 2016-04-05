package indi.yume.app.passwordbox.renderer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import indi.yume.app.passwordbox.R;
import indi.yume.app.passwordbox.model.database.DataTable;
import indi.yume.app.passwordbox.view.CircleView;
import indi.yume.tools.adapter_renderer.BaseRenderer;

/**
 * Created by bush2 on 2016/4/4.
 */
public class ItemRenderer extends BaseRenderer<DataTable> {
    @Bind(R.id.title_text_view)
    TextView titleTextView;
    @Bind(R.id.website_text_view)
    TextView websiteTextView;
    @Bind(R.id.head_text_view)
    CircleView headTextView;

    @Override
    public void render() {
        DataTable dataTable = getContent();

        headTextView.setText(dataTable.getTitle());
        headTextView.setColor(dataTable.getLabelColor());
        titleTextView.setText(dataTable.getTitle());
        websiteTextView.setText(dataTable.getWebsite());
    }

    @Override
    protected View inflate(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.item_layout, viewGroup, false);
    }

    @Override
    protected void findView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void setListener(View view) {

    }
}
