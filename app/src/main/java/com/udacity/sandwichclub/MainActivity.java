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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewSwitcher;

import com.udacity.sandwichclub.adapter.SandwichAdapter;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.sandwiches_listview)
    ListView listView;
    @BindView(R.id.sandwiches_gridview)
    GridView gridView;
    @BindView(R.id.view_switcher)
    ViewSwitcher switcher;

    //flag to indicate toggling between listview and gridview
    private boolean isListDisplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        isListDisplayed = true;

        //initialize array list to hold sandwich details
        List<Sandwich> sandwichArrayList = new ArrayList<>();
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        for (String json : sandwiches) {
            //parse the json for each sandwich and add the sandwich object to the array list
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            sandwichArrayList.add(sandwich);
        }
        //initialize custom adapter
        SandwichAdapter adapter = new SandwichAdapter(this, sandwichArrayList);

        //set up ListView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchDetailActivity(position, (ImageView) view.findViewById(R.id.image_iv));
            }
        });

        //set up GridView
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchDetailActivity(position, (ImageView) view.findViewById(R.id.image_iv));
            }
        });
    }

    /**
     * fires an intent from the main screen to the detailed screen
     *
     * @param position  position of the item
     * @param imageView reference to the sandwich imageview
     */
    private void launchDetailActivity(int position, ImageView imageView) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        //define shared element transition animation
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, ViewCompat.getTransitionName(imageView));
        startActivity(intent, options.toBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle toggling between listview and gridview using switcher
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
