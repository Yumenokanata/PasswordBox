package indi.yume.app.passwordbox.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import indi.yume.app.passwordbox.R;
import indi.yume.app.passwordbox.model.database.DataTable;
import indi.yume.app.passwordbox.renderer.ItemRenderer;
import indi.yume.tools.adapter_renderer.RendererAdapter;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_list_view)
    ListView mainListView;
    @Bind(R.id.add_item_button)
    Button addItemButton;

    private RendererAdapter<DataTable> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new RendererAdapter<DataTable>(DataTable.getAllData(), this, ItemRenderer.class);
        mainListView.setAdapter(adapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                long id = ((DataTable) adapter.getItem(i)).getId();
                startActivityForResult(ShowActivity.createIntent(MainActivity.this, id), 1);
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(EditActivity.createIntent(MainActivity.this, null), 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        adapter.setContentList(DataTable.getAllData());
        adapter.notifyDataSetChanged();
    }
}
