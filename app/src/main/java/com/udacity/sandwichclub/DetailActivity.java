package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.ImageLoadCallback;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_iv)
    ImageView ingredientsIv;
    @BindView(R.id.origin_tv)
    TextView placeOfOrigin;
    @BindView(R.id.also_known_tv)
    TextView alsoKnownAs;
    @BindView(R.id.description_tv)
    TextView description;
    @BindView(R.id.ingredients_tv)
    TextView ingredients;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        //display back button on the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        //load the sandwich image
        Picasso.with(this)
                .load(sandwich.getImage())
                //set error image
                .error(R.drawable.ic_error_outline)
                //implement custom image loaded callback
                .into(ingredientsIv, new ImageLoadCallback(progressBar, ingredientsIv, true));

        //set the title of the actionbar to the displayed sandwich's name
        setTitle(sandwich.getMainName());
    }

    /**
     * called when the data for the sandwich is unavailable
     */
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * updates the UI with sandwich data
     *
     * @param sandwich reference toe currently displayed sandwich object
     */
    private void populateUI(Sandwich sandwich) {

        //set the text for origin, description, alternate names and ingredients after due validation

        placeOfOrigin.setText(sandwich.getPlaceOfOrigin().length() > 0 ? sandwich.getPlaceOfOrigin() : getString(R.string.not_available));
        description.setText(sandwich.getDescription().length() > 0 ? sandwich.getDescription() : getString(R.string.not_available));

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if (alsoKnownAsList.size() > 0) {
            for (int i = 0; i < alsoKnownAsList.size(); i++) {
                //add bullet marker for each name
                alsoKnownAs.append("\u2022 ");
                alsoKnownAs.append(alsoKnownAsList.get(i));
                if (i < alsoKnownAsList.size() - 1) {
                    alsoKnownAs.append("\n");
                }
            }
        } else {
            alsoKnownAs.setText(R.string.not_available);
        }


        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList.size() > 0) {
            for (int i = 0; i < ingredientsList.size(); i++) {
                //add square marker for each intgredient
                ingredients.append("\u25AA ");
                ingredients.append(ingredientsList.get(i));
                if (i < ingredientsList.size() - 1) {
                    ingredients.append("\n");
                }
            }
        } else {
            ingredients.setText(R.string.not_available);
        }

    }

    //handle back navigation
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
