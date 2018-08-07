package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewSwitcher;

import com.udacity.sandwichclub.adapter.SandwichAdapter;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.internal.Utils;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private ViewSwitcher switcher;
    private boolean isListDisplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switcher = findViewById(R.id.view_switcher);
        isListDisplayed = true;

        List<Sandwich> sandwichArrayList = new ArrayList<>();
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        for (String json : sandwiches) {
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            sandwichArrayList.add(sandwich);
        }
        SandwichAdapter adapter = new SandwichAdapter(this, sandwichArrayList);

        // Simplification: Using a ListView instead of a RecyclerView
        ListView listView = findViewById(R.id.sandwiches_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchDetailActivity(position, (ImageView) view.findViewById(R.id.image_iv));
            }
        });

        GridView gridView = findViewById(R.id.sandwiches_gridview);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchDetailActivity(position, (ImageView) view.findViewById(R.id.image_iv));
            }
        });
    }

    private void launchDetailActivity(int position, ImageView imageView) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,imageView, ViewCompat.getTransitionName(imageView));
        startActivity(intent, options.toBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.toggleView) {
            if (isListDisplayed) {
                switcher.showNext();
                isListDisplayed = false;
                item.setIcon(R.drawable.ic_view_list);
            } else {
                switcher.showPrevious();
                isListDisplayed = true;
                item.setIcon(R.drawable.ic_view_module);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
