package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
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
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView placeOfOrigin = findViewById(R.id.origin_tv);
        TextView alsoKnownAs = findViewById(R.id.also_known_tv);
        TextView description = findViewById(R.id.description_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);

        placeOfOrigin.setText(sandwich.getPlaceOfOrigin().length() > 0 ? sandwich.getPlaceOfOrigin() : getString(R.string.not_available));
        description.setText(sandwich.getDescription().length() > 0 ? sandwich.getDescription() : getString(R.string.not_available));

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if (alsoKnownAsList.size() > 0) {
            for (int i = 0; i < alsoKnownAsList.size(); i++) {
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
            for (int i = 0; i < ingredientsList.size(); i ++) {
                ingredients.append(ingredientsList.get(i));
                if (i < ingredientsList.size() - 1) {
                      ingredients.append("\n");
                }
            }
        } else {
            ingredients.setText(R.string.not_available);
        }

    }
}
